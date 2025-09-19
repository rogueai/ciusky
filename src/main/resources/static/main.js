htmx.onLoad(function () {

    initFlowbite();

    document.body.addEventListener("showToast", (event) => {
        if (event.processed) {
            return true;
        }
        event.processed = true;
        const html = (event.detail || {}).value;

        const toastEl = document.getElementById('toast-default');
        if (!toastEl) {
            document.body.insertAdjacentHTML('beforeend', html);
        }
        else {
            toastEl.outerHTML = html;
        }

        // We call initFlowbite otherwise the close modal button does not work
        initFlowbite();

        // TODO: I yet have to find a better method to close the dialog automatically.
        clearTimeout(window.toastTimeout);
        const $triggerEl = document.getElementById('toastTrigger');
        window.toastTimeout = setTimeout(() => $triggerEl.click(), 5000);



    });
})
