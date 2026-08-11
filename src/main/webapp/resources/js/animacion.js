document.addEventListener("DOMContentLoaded", function () {
    iniciarReloj();
    iniciarGraficas();
});

// Variables globales para controlar y destruir instancias al recargar
let chartPresentesInstance = null;
let chartAusentesInstance = null;
let chartDonaInstance = null;
let chartTendenciaInstance = null;

// Reloj digital en vivo
function iniciarReloj() {
    function actualizar() {
        const ahora = new Date();
        const horas = String(ahora.getHours()).padStart(2, '0');
        const minutos = String(ahora.getMinutes()).padStart(2, '0');
        const segundos = String(ahora.getSeconds()).padStart(2, '0');
        const elementoReloj = document.getElementById("reloj-digital");
        if (elementoReloj) {
            elementoReloj.innerText = `${horas}:${minutos}:${segundos}`;
        }
    }
    actualizar();
    setInterval(actualizar, 1000);
}

// Inicialización de las 4 Gráficas con Chart.js
function iniciarGraficas() {
    if (typeof Chart === 'undefined') return;

    const dataContainer = document.getElementById("dashboard-data");
    if (!dataContainer) return;

    const total = parseInt(dataContainer.getAttribute("data-total"), 10) || 0;
    const presentes = parseInt(dataContainer.getAttribute("data-presentes"), 10) || 0;
    const ausentes = parseInt(dataContainer.getAttribute("data-ausentes"), 10) || 0;

    const commonScales = {
        y: {
            beginAtZero: true,
            ticks: { stepSize: 1, font: { family: "'Segoe UI', sans-serif", size: 12 }, color: '#8d99ae' },
            grid: { color: '#f0f2f5', drawBorder: false }
        },
        x: {
            ticks: { font: { family: "'Segoe UI', sans-serif", size: 12, weight: '600' }, color: '#2b2d42' },
            grid: { display: false }
        }
    };

    const commonPlugins = {
        legend: { display: false },
        tooltip: {
            backgroundColor: '#2b2d42',
            padding: 10,
            cornerRadius: 8,
            displayColors: false
        }
    };

    // 1. Gráfica de Barras - Presentes
    const canvasPresentes = document.getElementById("chartPresentes");
    if (canvasPresentes) {
        if (chartPresentesInstance) chartPresentesInstance.destroy();
        chartPresentesInstance = new Chart(canvasPresentes, {
            type: 'bar',
            data: {
                labels: ['Presentes Hoy', 'Total Planilla'],
                datasets: [{
                    label: 'Empleados',
                    data: [presentes, total],
                    backgroundColor: ['rgba(17, 153, 142, 0.85)', 'rgba(58, 123, 213, 0.85)'],
                    borderColor: ['#11998e', '#3a7bd5'],
                    borderWidth: 2,
                    borderRadius: 8,
                    barThickness: 45
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, plugins: commonPlugins, scales: commonScales }
        });
    }

    // 2. Gráfica de Barras - Ausentes
    const canvasAusentes = document.getElementById("chartAusentes");
    if (canvasAusentes) {
        if (chartAusentesInstance) chartAusentesInstance.destroy();
        chartAusentesInstance = new Chart(canvasAusentes, {
            type: 'bar',
            data: {
                labels: ['Ausentes Hoy', 'Total Planilla'],
                datasets: [{
                    label: 'Empleados',
                    data: [ausentes, total],
                    backgroundColor: ['rgba(235, 57, 65, 0.85)', 'rgba(58, 123, 213, 0.85)'],
                    borderColor: ['#eb3941', '#3a7bd5'],
                    borderWidth: 2,
                    borderRadius: 8,
                    barThickness: 45
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, plugins: commonPlugins, scales: commonScales }
        });
    }

    // 3. Gráfica de Dona - Porcentaje Asistencia vs Ausentismo
    const canvasDona = document.getElementById("chartDona");
    if (canvasDona) {
        if (chartDonaInstance) chartDonaInstance.destroy();
        chartDonaInstance = new Chart(canvasDona, {
            type: 'doughnut',
            data: {
                labels: ['Presentes', 'Ausentes'],
                datasets: [{
                    data: [presentes, ausentes],
                    backgroundColor: ['#11998e', '#eb3941'],
                    borderWidth: 3,
                    borderColor: '#ffffff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        position: 'bottom',
                        labels: { font: { family: "'Segoe UI', sans-serif", size: 12, weight: '600' } }
                    }
                },
                cutout: '70%'
            }
        });
    }

    // 4. Gráfica de Línea - Tendencia Semanal
    const canvasTendencia = document.getElementById("chartTendencia");
    if (canvasTendencia) {
        if (chartTendenciaInstance) chartTendenciaInstance.destroy();
        chartTendenciaInstance = new Chart(canvasTendencia, {
            type: 'line',
            data: {
                labels: ['Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb', 'Dom'],
                datasets: [{
                    label: 'Asistencias',
                    data: [total, Math.min(total, 2), presentes, total, total, 0, 0],
                    borderColor: '#3a7bd5',
                    backgroundColor: 'rgba(58, 123, 213, 0.1)',
                    fill: true,
                    tension: 0.4,
                    pointRadius: 5,
                    pointBackgroundColor: '#3a7bd5'
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, plugins: commonPlugins, scales: commonScales }
        });
    }
}