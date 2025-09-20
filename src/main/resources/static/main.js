htmx.onLoad(function () {

    initFlowbite();

    document.body.addEventListener("showToast", (event) => {
        if (event.processed) {
            return true;
        }
        event.processed = true;
        const html = (event.detail || {}).value;

        var toastEl = htmx.find("#toast-default")
        if (!toastEl) {
            htmx.swap("body", html, { swapStyle: 'beforeend'} );
        }
        else {
            htmx.swap("#toast-default", html, { swapStyle: 'innerHTML'} );
        }

        // We call initFlowbite otherwise the close modal button does not work
        initFlowbite();

        // TODO: I yet have to find a better method to close the dialog automatically.
        clearTimeout(window.toastTimeout);
        const $triggerEl = document.getElementById('toastTrigger');
        window.toastTimeout = setTimeout(() => $triggerEl.click(), 5000);

    });
})
