document.addEventListener('DOMContentLoaded', function() {
    // Загружаем настройки из localStorage
    const savedSettings = localStorage.getItem('appSettings');
    if (savedSettings) {
        const settings = JSON.parse(savedSettings);
        
        // Применяем настройки
        document.body.classList.toggle('high-contrast', settings.contrast);
        document.body.classList.toggle('grayscale', settings.grayscale);
        document.documentElement.style.setProperty('--animation-duration', settings.animation ? '0.3s' : '0s');
        document.body.style.fontSize = `${settings.fontSize}em`;
        
        // Применяем тему
        if (settings.theme === 'light') {
            document.getElementById('light-theme').disabled = false;
            document.getElementById('main-style').disabled = true;
        } else {
            document.getElementById('light-theme').disabled = true;
            document.getElementById('main-style').disabled = false;
        }
    }
});