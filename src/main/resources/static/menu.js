// Функция для построения меню с учетом роли пользователя
async function buildMenu() {
    const authKey = localStorage.getItem('institutionKey');
    if (!authKey) {
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(`/api/v1/institutions/${authKey}`, {
            headers: {
                'Authorization': authKey
            }
        });

        if (response.ok) {
            const user = await response.json();
            const isAdmin = user.role === 'ADMIN';
            renderMenu(isAdmin);
        }
    } catch (error) {
        console.error('Ошибка при проверке прав:', error);
    }
}

// Функция для отрисовки меню
function renderMenu(isAdmin) {
    const menuItems = [
        { link: 'index.html', text: 'Главная' },
        { link: 'marking.html', text: 'Маркировка' },
        { link: 'inventory.html', text: 'Инвентаризация' },
        isAdmin ? { link: 'personnel.html', text: 'Персоналии' } : null,
        { link: 'setting.html', text: 'Настройки' },
        { link: '#', text: 'Выход', onclick: 'logout()' }
    ].filter(item => item !== null);

    const menuContainer = document.querySelector('.menu');
    if (menuContainer) {
        menuContainer.innerHTML = menuItems.map(item =>
            `<li><a href="${item.link}" ${item.onclick ? `onclick="${item.onclick}"` : ''}>${item.text}</a></li>`
        ).join('');
    }
}

// Общие функции
function logout() {
    localStorage.removeItem('institutionKey');
    localStorage.removeItem('institutionName');
    window.location.href = 'login.html';
}

function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
        sidebar.classList.toggle('active');
    }
}

document.addEventListener('DOMContentLoaded', buildMenu);