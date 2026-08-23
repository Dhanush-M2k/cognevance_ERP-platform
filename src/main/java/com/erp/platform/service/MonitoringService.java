package com.erp.platform.service;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MonitoringService {

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private final OperatingSystemMXBean osMXBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public Map<String, Object> currentSnapshot() {
        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        double cpuLoad = osMXBean.getSystemCpuLoad();
        if (cpuLoad < 0) {
            cpuLoad = osMXBean.getProcessCpuLoad();
        }
        if (cpuLoad < 0) {
            cpuLoad = 0;
        }

        Duration uptime = Duration.ofMillis(runtimeMXBean.getUptime());

        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("timestamp", System.currentTimeMillis());
        snapshot.put("uptimeSeconds", uptime.getSeconds());
        snapshot.put("uptimeFormatted", formatDuration(uptime));
        snapshot.put("availableProcessors", runtime.availableProcessors());
        snapshot.put("cpuLoadPercent", round(cpuLoad * 100.0));
        snapshot.put("usedMemoryMb", toMb(usedMemory));
        snapshot.put("totalMemoryMb", toMb(totalMemory));
        snapshot.put("maxMemoryMb", toMb(maxMemory));
        snapshot.put("memoryUsagePercent", round((usedMemory * 100.0) / maxMemory));
        snapshot.put("liveThreadCount", threadMXBean.getThreadCount());
        snapshot.put("peakThreadCount", threadMXBean.getPeakThreadCount());
        snapshot.put("daemonThreadCount", threadMXBean.getDaemonThreadCount());
        snapshot.put("jvmName", runtimeMXBean.getVmName());
        snapshot.put("jvmVersion", runtimeMXBean.getVmVersion());
        snapshot.put("status", "UP");

        return snapshot;
    }

    private long toMb(long bytes) {
        return bytes / (1024 * 1024);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        sb.append(hours).append("h ")
          .append(minutes).append("m ")
          .append(seconds).append("s");
        return sb.toString();
    }
}
