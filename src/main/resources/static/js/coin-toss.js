document.addEventListener('DOMContentLoaded', function() {
    // Check if user is logged in
    const username = sessionStorage.getItem('username');
    const authSection = document.getElementById('auth-section');
    
    if (username) {
        authSection.innerHTML = `
            <span>Welcome, ${username}!</span>
            <a class="logout-btn" onclick="logout()">Logout</a>
        `;
        initializeCoinToss();
    } else {
        authSection.innerHTML = `
            <div class="auth-buttons">
                <a href="/auth/login" class="login-btn">Login</a>
                <a href="/auth/register" class="register-btn">Register</a>
            </div>
        `;
        document.getElementById('coin-container').innerHTML = `
            <div class="no-access">
                <p>Please login to use the coin toss feature.</p>
            </div>
        `;
        document.getElementById('toss-btn').disabled = true;
    }
});

let stompClient = null;
let currentCoinToss = null;
let tossHistory = [];

function initializeCoinToss() {
    // Initialize WebSocket connection
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        
        // Set up event listeners
        document.getElementById('toss-btn').addEventListener('click', tossCoin);
    });
}

function tossCoin() {
    const username = sessionStorage.getItem('username');
    if (!username) {
        alert('Please login to use the coin toss feature.');
        return;
    }
    
    // Disable the button during toss
    const tossBtn = document.getElementById('toss-btn');
    tossBtn.disabled = true;
    
    // Create a new coin toss
    fetch(`/api/coin-toss?username=${encodeURIComponent(username)}`, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(coinToss => {
        currentCoinToss = coinToss;
        
        // Subscribe to coin toss updates
        stompClient.subscribe('/topic/coin-toss/' + coinToss.id, function(message) {
            const result = JSON.parse(message.body);
            showCoinTossResult(result);
        });
        
        // Trigger the coin toss
        return fetch(`/api/coin-toss/${coinToss.id}/toss`, {
            method: 'POST'
        });
    })
    .then(response => response.json())
    .then(result => {
        // Animation will be handled by the WebSocket message
        animateCoinToss(result.result);
    })
    .catch(error => {
        console.error('Error tossing coin:', error);
        alert('Failed to toss coin. Please try again.');
        tossBtn.disabled = false;
    });
}

function animateCoinToss(result) {
    const coin = document.getElementById('coin');
    const resultElement = document.getElementById('coin-result');
    
    // Reset classes
    coin.className = 'coin';
    resultElement.className = 'coin-result';
    
    // Force a reflow
    void coin.offsetWidth;
    
    // Add the appropriate flip animation class
    if (result === 'HEADS') {
        coin.classList.add('flip-heads');
    } else {
        coin.classList.add('flip-tails');
    }
    
    // Wait for animation to complete
    setTimeout(() => {
        showCoinTossResult({ result: result });
    }, 1000);
}

function showCoinTossResult(result) {
    const resultElement = document.getElementById('coin-result');
    const tossBtn = document.getElementById('toss-btn');
    
    // Show the result
    resultElement.textContent = `Result: ${result.result}`;
    resultElement.classList.add('visible');
    
    // Add to history
    addToHistory(result);
    
    // Re-enable the button
    tossBtn.disabled = false;
}

function addToHistory(result) {
    // Add to history array
    tossHistory.unshift({
        result: result.result,
        timestamp: new Date().toLocaleTimeString()
    });
    
    // Limit history to 10 items
    if (tossHistory.length > 10) {
        tossHistory.pop();
    }
    
    // Update history display
    updateHistoryDisplay();
}

function updateHistoryDisplay() {
    const historyList = document.getElementById('history-list');
    historyList.innerHTML = '';
    
    if (tossHistory.length === 0) {
        historyList.innerHTML = '<li class="history-item">No tosses yet</li>';
        return;
    }
    
    tossHistory.forEach(item => {
        const listItem = document.createElement('li');
        listItem.className = 'history-item';
        listItem.innerHTML = `
            <span>${item.timestamp}</span>
            <span class="${item.result.toLowerCase()}">${item.result}</span>
        `;
        historyList.appendChild(listItem);
    });
}

function logout() {
    // Disconnect WebSocket
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    
    // Clear session and redirect
    sessionStorage.removeItem('username');
    window.location.href = '/auth/login';
} 