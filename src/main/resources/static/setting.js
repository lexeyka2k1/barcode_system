const settings = {
    theme: 'light',
    animation: true,
    contrast: false,
    grayscale: false,
    fontSize: 1
};

function applySettings() {
    // Применение темы
    const lightTheme = document.getElementById('light-theme');
    const mainStyle = document.getElementById('main-style');
    if (settings.theme === 'light') {
        lightTheme.disabled = false;

    } else {
        lightTheme.disabled = true;

    }

    // Анимации
    document.documentElement.style.setProperty('--animation-duration', settings.animation ? '0.3s' : '0s');

    // Контраст
    const contrastStyle = document.getElementById('high-contrast-style');
    if (contrastStyle) contrastStyle.disabled = !settings.contrast;
    document.documentElement.classList.toggle('high-contrast', settings.contrast);

    // Градации серого
    const grayscaleStyle = document.getElementById('grayscale-style');
    if (grayscaleStyle) grayscaleStyle.disabled = !settings.grayscale;
    document.documentElement.classList.toggle('grayscale', settings.grayscale);

    // Размер шрифта
    document.body.style.fontSize = `${settings.fontSize}em`;

    // Сохраняем
    localStorage.setItem('appSettings', JSON.stringify(settings));

    // Обновление текстов кнопок (если они есть)
    const animBtn = document.getElementById('toggle-animation');
    const contrastBtn = document.getElementById('toggle-contrast');
    const grayBtn = document.getElementById('toggle-grayscale');

    if (animBtn) animBtn.textContent = settings.animation ? 'Выключить' : 'Включить';
    if (contrastBtn) contrastBtn.textContent = settings.contrast ? 'Выключить' : 'Включить';
    if (grayBtn) grayBtn.textContent = settings.grayscale ? 'Выключить' : 'Включить';
}

function loadSettings() {
    const saved = localStorage.getItem('appSettings');
    if (saved) {
        Object.assign(settings, JSON.parse(saved));
    } else {
        // Если тема не задана, подхватим системную
        const prefersLight = window.matchMedia('(prefers-color-scheme: light)').matches;
        settings.theme = prefersLight ? 'light' : 'dark';
    }

    applySettings();
}

document.addEventListener('DOMContentLoaded', () => {
    loadSettings();

    // Навешиваем кнопки (если они есть на странице)
    document.getElementById('theme-toggle')?.addEventListener('click', () => {
        settings.theme = (settings.theme === 'light') ? 'dark' : 'light';
        applySettings();
    });

    document.getElementById('toggle-animation')?.addEventListener('click', () => {
        settings.animation = !settings.animation;
        applySettings();
    });

    document.getElementById('toggle-contrast')?.addEventListener('click', () => {
        settings.contrast = !settings.contrast;
        settings.grayscale = false; // Отключаем grayscale при включении контраста
        applySettings();
    });

    document.getElementById('toggle-grayscale')?.addEventListener('click', () => {
        settings.grayscale = !settings.grayscale;
        settings.contrast = false; // Отключаем контраст при включении grayscale
        applySettings();
    });

    document.getElementById('increase-font')?.addEventListener('click', () => {
        settings.fontSize = Math.min(1.5, settings.fontSize + 0.1);
        applySettings();
    });

    document.getElementById('decrease-font')?.addEventListener('click', () => {
        settings.fontSize = Math.max(0.8, settings.fontSize - 0.1);
        applySettings();
    });
});
