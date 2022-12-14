/**
 * @author Aviad Shikloshi
 */

var resolverPrefix = 'credentialsConfig';

function updateViewForCredentialsMethod(useCredentialsPlugin) {
    toggleCredentialsPluginFromView(useCredentialsPlugin);
}

function toggleCredentialsPluginFromView(show) {
    var newDisplayStyle = show ? '' : 'none';
    var resolverTables = getElementsWithIdPrefix(resolverPrefix);
    setNewDisplayStyle(resolverTables, newDisplayStyle);
}

function setNewDisplayStyle(elements, display) {
    var elementsIndex;
    for (elementsIndex = 0; elementsIndex < elements.length; elementsIndex++)
        elements[elementsIndex].style.display = display;
}

/**
 * This will return an array of HTML elements with Id that start with prefix
 * @param prefix Id prefix to search for
 * @returns {*}
 */
function getElementsWithIdPrefix(prefix) {
    if (document.querySelectorAll) {
        return Array.prototype.slice.call(document.querySelectorAll('*[id^="' + prefix + '"]'));
    } else {
        // Old browsers support
        var elements = document.getElementsByTagName('*');
        var relevantElements = [];
        for (var i = 0; i < elements.length; i++) {
            if (element.id && element.id.indexOf(prefix) === 0) {
                relevantElements.push(element);
            }
        }
        return relevantElements;
    }
}

document.addEventListener('DOMContentLoaded', function (event) {
    var useCredentialsPluginInput = document.getElementById('useCredentialsPlugin');
    useCredentialsPluginInput.checked = JSON.parse(useCredentialsPluginInput.value);
    updateViewForCredentialsMethod(useCredentialsPluginInput.checked);
});
