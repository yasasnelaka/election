function showAlert(type, title, message) {
    Swal.fire({
        icon: type,
        title: title,
        text: message,
        // footer: '<a href="">Why do I have this issue?</a>'
    });
}