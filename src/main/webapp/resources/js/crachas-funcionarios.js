const fileInput = document.getElementById('uploadPhoto'),
previewPhoto = document.querySelector('statusFoto');

const loadImage = () => {
    let file = fileInput.files[0];
    if(!file) return;
    previewPhoto.src = URL.createObjectURL(file);
}

fileInput.addEventListener("change", loadImage);