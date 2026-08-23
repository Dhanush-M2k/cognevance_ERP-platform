
const CHART_COLORS = ['#2952e3', '#0fb98a', '#f5a524', '#e5484d', '#7c3aed', '#3b82f6'];

function renderRevenueChart(labels, values) {
    const ctx = document.getElementById('revenueChart');
    if (!ctx) return;
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Revenue (₹)',
                data: values,
                borderColor: '#2952e3',
                backgroundColor: 'rgba(41,82,227,0.08)',
                fill: true,
                tension: 0.35,
                pointRadius: 4,
                pointBackgroundColor: '#2952e3'
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, grid: { color: '#eef0f6' } },
                x: { grid: { display: false } }
            }
        }
    });
}

function renderInventoryChart(labels, values) {
    const ctx = document.getElementById('inventoryChart');
    if (!ctx) return;
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: CHART_COLORS,
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { position: 'bottom', labels: { boxWidth: 12, font: { size: 11 } } } }
        }
    });
}

function renderRatingChart(labels, values) {
    const ctx = document.getElementById('ratingChart');
    if (!ctx) return;
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Reviews',
                data: values,
                backgroundColor: '#f5a524',
                borderRadius: 6,
                maxBarThickness: 34
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, ticks: { precision: 0 }, grid: { color: '#eef0f6' } },
                x: { grid: { display: false } }
            }
        }
    });
}
