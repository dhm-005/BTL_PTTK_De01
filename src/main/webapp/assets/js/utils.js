document.querySelectorAll(".clickable").forEach(item => {
    item.addEventListener("click", function () {
        window.location.href = this.dataset.href;
    });
});
