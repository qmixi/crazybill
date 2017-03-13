(function () {
    var app = document.getElementById('app');
    var form;

    var createElement = function (tag, attributes) {
        var element = document.createElement(tag);

        for (var key in attributes) {
            if (attributes.hasOwnProperty(key)) {
                element[key] = attributes[key];
            }
        }

        return element;
    };

    var appendChildren = function (parent, children) {
        children.forEach(function (child) {
            parent.appendChild(child);
        });
    };

    var submitForm = function (ev) {
        ev.preventDefault();
        var data = new FormData(form);
        var json = {
            name: data.get('name')
        };

        fetch('/bills/', {
                method: 'POST',
                body: JSON.stringify(json),
                headers: {
                    'contentType': 'application/json'
                }
            })
            .then(console.log)
            .catch(console.error);
    };

    var generateForm = function () {
        form = createElement('form');
        var input = createElement('input', {
            type: 'text',
            name: 'name',
            id: 'name'
        });
        var label = createElement('label', {
            htmlFor: 'name',
            innerHTML: 'Nazwa rachunku'
        });
        var button = createElement('button', {
            type: 'submit',
            innerHTML: 'Zapisz'
        });

        button.onclick = submitForm;

        appendChildren(form, [input, label, button]);
        app.appendChild(form);
    };

    generateForm();
})();
