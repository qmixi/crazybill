(function () {
    var form = document.getElementById('new-bill');
    form.onsubmit = function (ev) {
        ev.preventDefault();
        var data = new FormData(form);

        fetch('/bills', {
            method: 'POST',
            body: data
        })
        .then(console.log)
        .catch(console.error);
    };
})();
