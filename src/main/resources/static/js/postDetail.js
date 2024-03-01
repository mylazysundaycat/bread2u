
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById("btn-delete").addEventListener("click", function() {
        var popup = document.getElementById("modal-bg");
        popup.style.display = "block";
        var popup = document.getElementById("modal-wrap");
        popup.style.display = "block";
    });
});


document.addEventListener('DOMContentLoaded', function() {
    document.getElementById("modal-bg").addEventListener("click", function() {
        var popup = document.getElementById("modal-bg");
        popup.style.display = "none";
        var popup = document.getElementById("modal-wrap");
        popup.style.display = "none";
    });
});
