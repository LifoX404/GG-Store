document.addEventListener('DOMContentLoaded', function () {
    const currentPath = window.location.pathname; // Ej: /admin/productos/editar/50524f...
    const navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(link => {
        const href = link.getAttribute('href');

        // Verifica si el href está contenido dentro del path actual
        if (currentPath.startsWith(href)) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });
});
