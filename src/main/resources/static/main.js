htmx.onLoad(function () {

    initFlowbite();

    // Handles HX-Trigger: { "showToast": { "message": "...", "success": true } }
    document.body.addEventListener("showToast", (event) => {
        const { message, success } = event.detail || {};
        const type = success === false ? 'error' : 'success';
        showToast(message || 'Done!', 5000, type);
    });
})


// HTMX toast notification
// TODO: Quick and dirty toast
const showToast = (message, duration = 5000, type = 'success') => {
    // target element that will be dismissed
    const $targetEl = document.getElementById('toast');
    // optional trigger element
    const $triggerEl = document.getElementById('toastTrigger');
    const $toastText = document.getElementById('toast-text');
    const $toastSuccessEl = document.getElementById('toast-success');
    const $toastFailEl = document.getElementById('toast-fail');

    $targetEl.classList.remove('hidden');
    $targetEl.classList.remove('opacity-0');

    if (type == 'success') {
        $toastFailEl.classList.remove('inline-flex');
        $toastSuccessEl.classList.add('inline-flex');
    } else {
        $toastSuccessEl.classList.remove('inline-flex');
        $toastFailEl.classList.add('inline-flex');
    }

    // Set the message
    $toastText.textContent = message;

    // Auto-dismiss
    clearTimeout(window.toastTimeout);
    window.toastTimeout = setTimeout(() => $triggerEl.click(), duration);
};
