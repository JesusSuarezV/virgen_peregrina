document.addEventListener('DOMContentLoaded', () => {
    const entradaImagen = document.querySelector('.entrada-imagen');
    const fileInput = document.getElementById('fileInput');

    if (entradaImagen && fileInput) {
        entradaImagen.addEventListener('click', () => {
            fileInput.click();
        });
    } else {
        console.error('Elemento(s) no encontrado(s) en el DOM');
    }
});
