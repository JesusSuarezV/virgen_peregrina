const replicas = document.querySelector('.replicas');
const peregrinaciones = document.querySelector('.peregrinaciones');
const noticias = document.querySelector('.noticias');
const iniciarsesion = document.querySelector('.iniciar-sesion');
const submenuItem = replicas.querySelector('.nav-subitem');



replicas.addEventListener('click', () => {
    const submenu = replicas.querySelector('.nav-subitem'); // Busca el submenú dentro de 'replicas'
    submenu.style.display = submenu.style.display === 'none' ? 'block' : 'none';
});

peregrinaciones.addEventListener('click', () => {
    const submenu = peregrinaciones.querySelector('.nav-subitem'); // Busca el submenú dentro de 'peregrinaciones'
    submenu.style.display = submenu.style.display === 'none' ? 'block' : 'none';
});

noticias.addEventListener('click', () => {
    const submenu = noticias.querySelector('.nav-subitem'); // Busca el submenú dentro de 'noticias'
    submenu.style.display = submenu.style.display === 'none' ? 'block' : 'none';
});

iniciarsesion.addEventListener('click', () => {
    const submenu = iniciarsesion.querySelector('.nav-subitem'); // Busca el submenú dentro de 'iniciar-sesion'
    submenu.style.display = submenu.style.display === 'none' ? 'block' : 'none';
});



function confirmarOcultar() {
	return confirm('¿Estás seguro de que deseas eliminar esta entrada?');
}


function closeNotification(notificationId) {
    var notification = document.getElementById(notificationId);
    notification.style.display = "none";
}