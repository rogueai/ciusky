window.addEventListener("load", (event) => {
  initFlowbite();
});

htmx.on('#form-upload', 'htmx:beforeRequest', function(evt) {
    const pr = htmx.find('#upload-progress');
    htmx.toggleClass(pr, 'hidden');
});
htmx.on('#form-upload', 'htmx:beforeSwap', function(evt) {
    const pr = htmx.find('#upload-progress');
    htmx.toggleClass(pr, 'hidden');
});

htmx.onLoad(function () {

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
            htmx.swap("#toast-default", html, { swapStyle: 'outerHTML'} );
        }

        // We call initFlowbite otherwise the close modal button does not work
        initFlowbite();

        // TODO: I yet have to find a better method to close the dialog automatically.
        clearTimeout(window.toastTimeout);
        const $triggerEl = document.getElementById('toastTrigger');
        window.toastTimeout = setTimeout(() => $triggerEl.click(), 2000);

    });
})

function toggleWideImage(el) {
    htmx.toggleClass(el, "md:col-span-4");
    htmx.toggleClass(el, "col-span-2");
    htmx.toggleClass(el, "w-full");
    htmx.toggleClass(el, "bg-gray-700");
    htmx.toggleClass(el, "p-2");
    const img = htmx.find(el, "img")
    let newSrc = img.dataset.src;
    img.dataset.src = img.src;
    img.src = newSrc;
}
