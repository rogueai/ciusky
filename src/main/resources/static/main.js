htmx.onLoad(function () {
    
    initFlowbite();

    // Handles HX-Trigger: { "showToast": { "message": "...", "success": true } }
    document.body.addEventListener("showToast", (event) => {
        const { message, success } = event.detail || {};
        const type = success === false ? 'error' : 'success';
        showToast(message || 'Done!', 3000, type);
    });
})


// HTMX toast notification
// TODO: Quick and dirty toast
window.showToast = (message, duration = 3000, type = 'success') => {
    const toast = document.getElementById('toast');
    const toastText = document.getElementById('toast-text');
    const toastDismissBtn = document.getElementById('toastDismissBtn');

    // Set the message
    toastText.textContent = message;

    // Reset
    toast.classList.add('animate-slide-in');
    toast.classList.remove('hidden');
    toast.classList.remove('opacity-0');

    // Auto-dismiss
    clearTimeout(window.toastTimeout);
    //TODO: For now we automatically click the button after duration
    window.toastTimeout = setTimeout(() => toastDismissBtn.click(), duration);

};
