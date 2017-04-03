(function () {
    var form = document.getElementById('new-bill');
    var links = document.getElementById('links');
    var indicator = document.getElementById('indicator');

    form.onsubmit = function (ev) {
        onSubmitBegin();
        ev.preventDefault();
        var data = new FormData(form);
        var errorMessage = validateData(data);
        if (errorMessage) {
            alert(errorMessage);
            onSubmitEnd();
            return;
        }
        var json = {
            name: data.get('name'),
            positions: []
        };

        fetch('/bills', {
            method: 'POST',
            body: JSON.stringify(json),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(addLinkToNewBill.bind(json))
            .catch(console.error)
    };

    function addLinkToNewBill (response) {
        var link = document.createElement('a');
        link.href = response.headers.get('location');
        link.innerHTML = this.name;
        link.target = '_blank';
        link.className = 'link';

        links.appendChild(link);
        onSubmitEnd();
    }

    function onSubmitBegin() {
        indicator.classList.add('active');
    }

    function onSubmitEnd() {
        indicator.classList.remove('active');
    }

    function validateData(data) {
        var name = data.get('name');
        var message;
        if (name.length === 0) {
            message = 'Nazwa nie może być pusta';
        }
        return message;
    }
})();
