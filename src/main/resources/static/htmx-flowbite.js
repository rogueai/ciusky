
htmx.onLoad(function (target) {
    initFlowbiteHtmx(target);
});

function initFlowbiteHtmx(target) {
    initDropdownsHtmx(target);
    initDismissesHtmx(target);
}

function initDropdownsHtmx(rootElement) {
    (rootElement || document).querySelectorAll('[data-dropdown-toggle]').forEach(($triggerEl) => {
        const dropdownId = $triggerEl.getAttribute('data-dropdown-toggle');
        const $dropdownEl = document.getElementById(dropdownId);
        if ($dropdownEl) {
            const placement = $triggerEl.getAttribute(
                'data-dropdown-placement'
            );
            const offsetSkidding = $triggerEl.getAttribute(
                'data-dropdown-offset-skidding'
            );
            const offsetDistance = $triggerEl.getAttribute(
                'data-dropdown-offset-distance'
            );
            const triggerType = $triggerEl.getAttribute(
                'data-dropdown-trigger'
            );
            const delay = $triggerEl.getAttribute('data-dropdown-delay');
            const ignoreClickOutsideClass = $triggerEl.getAttribute(
                'data-dropdown-ignore-click-outside-class'
            );
            new Dropdown(
                $dropdownEl,
                $triggerEl,
                {
                    placement: placement ? placement : 'bottom',
                    triggerType: triggerType ? triggerType: 'click',
                    offsetSkidding: offsetSkidding ? parseInt(offsetSkidding) : 0,
                    offsetDistance: offsetDistance ? parseInt(offsetDistance) : 10,
                    delay: delay ? parseInt(delay) : 100,
                    ignoreClickOutsideClass: ignoreClickOutsideClass ? ignoreClickOutsideClass : false,
                }
            );
        } else {
            console.error(
                `The dropdown element with id "${dropdownId}" does not exist. Please check the data-dropdown-toggle attribute.`
            );
        }
    });
}

function initDismissesHtmx(rootElement) {
    (rootElement || document).querySelectorAll('[data-dismiss-target]').forEach(($triggerEl) => {
        const targetId = $triggerEl.getAttribute('data-dismiss-target');
        const $dismissEl = document.querySelector(targetId);
        if ($dismissEl) {
            new Dismiss($dismissEl, $triggerEl);
        } else {
            console.error(
                `The dismiss element with id "${targetId}" does not exist. Please check the data-dismiss-target attribute.`
            );
        }
    });
}
