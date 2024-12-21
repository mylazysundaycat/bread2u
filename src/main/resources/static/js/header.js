function popUp() {
    var modalPop = $('.logout-modal-wrapper');
    if (modalPop.is(':visible')) {
        modalPop.hide(); // If modalPop is visible, hide it
    } else {
        modalPop.show(); // If modalPop is hidden, show it
    }
}