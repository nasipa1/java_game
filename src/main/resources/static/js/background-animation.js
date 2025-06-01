// Background animation with floating X's and O's
document.addEventListener('DOMContentLoaded', function() {
    // Create background animation container
    const backgroundAnimation = document.createElement('div');
    backgroundAnimation.className = 'background-animation';
    document.body.appendChild(backgroundAnimation);
    
    // Number of symbols to create - increased for better coverage
    const symbolCount = 80;
    
    // Create floating symbols
    for (let i = 0; i < symbolCount; i++) {
        createFloatingSymbol(backgroundAnimation, i);
    }
    
    // Handle window resize to ensure full coverage
    window.addEventListener('resize', function() {
        // Check if we need to add more symbols for larger screens
        const currentSymbols = backgroundAnimation.querySelectorAll('.floating-symbol').length;
        const windowWidth = window.innerWidth;
        
        // Add more symbols for wider screens
        if (windowWidth > 1200 && currentSymbols < 100) {
            const additionalCount = Math.min(20, 100 - currentSymbols);
            for (let i = 0; i < additionalCount; i++) {
                createFloatingSymbol(backgroundAnimation, currentSymbols + i);
            }
        }
    });
});

function createFloatingSymbol(container, index) {
    // Create symbol element
    const symbol = document.createElement('div');
    
    // Alternate between X and O
    const isX = index % 2 === 0;
    symbol.textContent = isX ? 'X' : 'O';
    symbol.className = `floating-symbol ${isX ? 'symbol-x' : 'symbol-o'}`;
    
    // Add random path and size classes
    const pathClass = `path-${Math.floor(Math.random() * 20) + 1}`;
    const sizeClass = `size-${Math.floor(Math.random() * 5) + 1}`;
    symbol.classList.add(pathClass, sizeClass);
    
    // Add random horizontal position variation within the path
    const randomOffset = Math.random() * 20 - 10; // -10% to +10% for better distribution
    const currentLeft = parseFloat(window.getComputedStyle(symbol).left) || 0;
    symbol.style.left = `calc(${currentLeft}% + ${randomOffset}%)`;
    
    // Add random starting position vertically
    const randomStartPosition = Math.random() * 100; // 0% to 100% of viewport height
    symbol.style.top = `${randomStartPosition}vh`;
    
    // Add to container
    container.appendChild(symbol);
    
    // Replace symbol with a new one after animation completes
    const animationDuration = parseFloat(window.getComputedStyle(symbol).animationDuration) * 1000;
    setTimeout(() => {
        symbol.remove();
        createFloatingSymbol(container, index);
    }, animationDuration);
} 