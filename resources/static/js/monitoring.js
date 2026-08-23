
const MAX_POINTS = 30;
const cpuHistory = [];
const memHistory = [];
const timeLabels = [];

const cpuChartCtx = document.getElementById('cpuChart');
const memChartCtx = document.getElementById('memChart');

const cpuChart = new Chart(cpuChartCtx, {
    type: 'line',
    data: {
        labels: timeLabels,
        datasets: [{
            label: 'CPU %',
            data: cpuHistory,
            borderColor: '#2952e3',
            backgroundColor: 'rgba(41,82,227,0.08)',
            fill: true,
            tension: 0.3,
            pointRadius: 0
        }]
    },
    options: {
        responsive: true,
        animation: false,
        plugins: { legend: { display: false } },
        scales: {
            y: { min: 0, max: 100, grid: { color: '#eef0f6' } },
            x: { grid: { display: false }, ticks: { maxTicksLimit: 6 } }
        }
    }
});

const memChart = new Chart(memChartCtx, {
    type: 'line',
    data: {
        labels: timeLabels,
        datasets: [{
            label: 'Memory (MB)',
            data: memHistory,
            borderColor: '#0fb98a',
            backgroundColor: 'rgba(15,185,138,0.08)',
            fill: true,
            tension: 0.3,
            pointRadius: 0
        }]
    },
    options: {
        responsive: true,
        animation: false,
        plugins: { legend: { display: false } },
        scales: {
            y: { min: 0, grid: { color: '#eef0f6' } },
            x: { grid: { display: false }, ticks: { maxTicksLimit: 6 } }
        }
    }
});

function pushPoint(arr, value) {
    arr.push(value);
    if (arr.length > MAX_POINTS) arr.shift();
}

async function refreshMonitoring() {
    try {
        const res = await fetch('/api/monitoring/live');
        if (!res.ok) return;
        const data = await res.json();

        document.getElementById('cpuValue').textContent = data.cpuLoadPercent + '%';
        document.getElementById('cpuBar').style.width = Math.min(data.cpuLoadPercent, 100) + '%';

        document.getElementById('memValue').textContent = data.usedMemoryMb + ' MB';
        document.getElementById('memBar').style.width = data.memoryUsagePercent + '%';

        document.getElementById('threadValue').textContent = data.liveThreadCount;
        document.getElementById('uptimeValue').textContent = data.uptimeFormatted;

        document.getElementById('jvmName').textContent = data.jvmName;
        document.getElementById('jvmVersion').textContent = data.jvmVersion;
        document.getElementById('processors').textContent = data.availableProcessors;
        document.getElementById('peakThreads').textContent = data.peakThreadCount;
        document.getElementById('daemonThreads').textContent = data.daemonThreadCount;
        document.getElementById('maxMemory').textContent = data.maxMemoryMb + ' MB';

        const now = new Date();
        const label = now.getHours().toString().padStart(2, '0') + ':' +
                      now.getMinutes().toString().padStart(2, '0') + ':' +
                      now.getSeconds().toString().padStart(2, '0');

        timeLabels.push(label);
        if (timeLabels.length > MAX_POINTS) timeLabels.shift();

        pushPoint(cpuHistory, data.cpuLoadPercent);
        pushPoint(memHistory, data.usedMemoryMb);

        cpuChart.update();
        memChart.update();

        document.getElementById('lastUpdated').textContent = 'updated at ' + label;
    } catch (e) {
        console.error('Monitoring refresh failed', e);
    }
}

refreshMonitoring();
setInterval(refreshMonitoring, 3000);
