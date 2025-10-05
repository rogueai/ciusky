window.document.addEventListener("showToast", (event) => {
    clearTimeout(window.toastTimeout);
    window.toastTimeout = setTimeout(() => {
        const detail = (event.detail || {});

        const html = detail.html;
        const timeout = detail.timeout;

        var toastEl = htmx.find("#toast-default")
        htmx.swap("#toast-default", html, { swapStyle: 'outerHTML'} );

        if (timeout > 0) {
            const $triggerEl = document.getElementById('toastTrigger');
            window.toastTimeout = setTimeout(() => $triggerEl.click(), timeout);
        }
    }, 100);
});

function toggleWideImage(el) {
    htmx.toggleClass(el, "md:col-span-4");
    htmx.toggleClass(el, "col-span-2");
    htmx.toggleClass(el, "w-full");
    htmx.toggleClass(el, "bg-gray-700");
    htmx.toggleClass(el, "p-2");

    const $img = htmx.find(el, "img")
    let newSrc = $img.dataset.src;
    $img.dataset.src = $img.src;
    $img.src = newSrc;

    const $fileName = htmx.find(el, ".csk-gallery-fileName");
    htmx.toggleClass($fileName, "hidden");

}

(function() {
    let api;
    htmx.defineExtension('tag', {
        init: function(apiRef) {
            api = apiRef;
        },
        onEvent : function(name, evt) {
            if (name === 'htmx:beforeProcessNode') {

               // The form that contains me
               const formEl = htmx.closest(evt.target, 'form');
               if (!formEl) {
                    throw new Error("Form not found");
               }

               htmx.on(evt.target, "keydown", function(evt) {
                   if (evt.keyCode === 13) {
                         // Prevents the form to be submitted
                       evt.preventDefault();
                   }
               });

               // POST
               const post_path = api.getAttributeValue(evt.target, "hx-tag-post");
               const post_target = api.getAttributeValue(evt.target, "hx-tag-post-target");

               // GET
               const get_path = api.getAttributeValue(evt.target, "hx-tag-get");
               const get_target = api.getAttributeValue(evt.target, "hx-tag-get-target");

               htmx.on(evt.target, "keyup", function(evt) {
                 if (evt.keyCode === 13) {
                    // Enter
                    const values = htmx.values(formEl);
                    htmx.ajax('POST', post_path, { values: values,  target: post_target } );
                 }
                 else {
                    // Simulate trigger changed
                    if (event.target.prevValue != evt.target.value) {
                        event.target.prevValue = evt.target.value;
                        const values = {
                            [evt.target.name] : evt.target.value
                        };
                        htmx.ajax('GET', get_path, { values: values, target: get_target, swap:'outerHTML'});
                    }
                 }
               });
            }
        }
    });
})();
