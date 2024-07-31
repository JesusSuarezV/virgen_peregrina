document.addEventListener('DOMContentLoaded', () => {
    const entradaImagen = document.querySelector('.entrada-imagen');
    const fileInput = document.getElementById('fileInput');

    if (entradaImagen && fileInput) {
        entradaImagen.addEventListener('click', () => {
            fileInput.click();
        });

        fileInput.addEventListener('change', () => {
            const file = fileInput.files[0];
            if (file) {
                const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp']; // Tipos de imagen permitidos
                if (!allowedTypes.includes(file.type)) {
                    alert("Por favor, suba un archivo de imagen válido (JPG, PNG, GIF, WEBP).");
                    fileInput.value = ""; // Limpiar el input
                    entradaImagen.childNodes[0].textContent= "Seleccionar Imagen"
                } else {
                     entradaImagen.childNodes[0].textContent = file.name; // Mostrar nombre del archivo seleccionado // Mostrar nombre del archivo seleccionado
                }
            }
        });
    } else {
        console.error('Elemento(s) no encontrado(s) en el DOM');
    }
});

