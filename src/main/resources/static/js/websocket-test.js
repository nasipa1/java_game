/**
 * Test script to verify that WebJars are properly loaded
 */
document.addEventListener('DOMContentLoaded', function() {
    // Check if SockJS is loaded
    if (typeof SockJS !== 'undefined') {
        console.log('SockJS is loaded successfully!');
    } else {
        console.error('SockJS is not loaded!');
    }
    
    // Check if Stomp is loaded
    if (typeof Stomp !== 'undefined') {
        console.log('Stomp is loaded successfully!');
    } else {
        console.error('Stomp is not loaded!');
    }
}); 