async function handleResponse(response, action) {
    const responseText = await response.text();
    if (response.ok) {
        showAlert(`${action} successful: ${responseText}`, true);
    } else {
        showAlert(`${action} failed: ${responseText}`, false);
    }
}

function showAlert(message, isSuccess) {
    alert((isSuccess ? '✅ ' : '❌ ') + message);
}

function clearForm(form) {
    form.querySelectorAll('input').forEach(input => {
        if (input.type !== 'submit') input.value = '';
    });
}

function formatTimestamp(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString('en-US', {
        month: 'short', // "Mar"
        day: 'numeric', // "12"
        year: 'numeric', // "2025"
        hour: 'numeric', // "8"
        minute: 'numeric', // "01"
        hour12: true // "PM"
    });
}

function displayMessages(messages) {
    console.log('Messages received:', messages); // Log the messages array
    const container = document.getElementById('messagesContainer');
    container.innerHTML = '';

    if (messages.length === 0) {
        container.innerHTML = '<div class="message">No messages found</div>';
        return;
    }

    messages.forEach(msg => {
        console.log('Parsed message:', { timestamp: msg.timestamp, content: msg.content }); // Log each message
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message';
        messageDiv.innerHTML = `
            <div class="timestamp">${formatTimestamp(msg.timestamp)}</div>
            <div class="content">${msg.content}</div>
        `;
        container.appendChild(messageDiv);
    });
}

// Add event listeners after defining all functions
document.addEventListener('DOMContentLoaded', () => {
    // Read Messages Form Handler
    document.getElementById('readMessagesForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('readUsername').value;
        const password = document.getElementById('readPassword').value;

        console.log('Reading messages for:', { username, password });

        try {
            const response = await fetch(`/message?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`);

            console.log('Read messages response:', response);

            if (response.ok) {
                const messages = await response.json(); // Parse the JSON response
                displayMessages(messages);
                clearForm(e.target);
                showAlert('Messages loaded successfully!', true);
            } else {
                const errorText = await response.text();
                showAlert(`Error ${response.status}: ${errorText}`, false);
            }
        } catch (error) {
            console.error('Read messages error:', error);
            showAlert('Error: ' + error.message, false);
        }
    });
});

    // Send Message Form Handler
    document.getElementById('sendMessageForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const receiver = document.getElementById('receiverUsername').value;
        const message = document.getElementById('messageContent').value;

        console.log('Sending message:', { receiver, message });

        if (message.includes('\n')) {
            showAlert('Message cannot contain newline characters!', false);
            return;
        }

        try {
            const response = await fetch('/message', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    username: receiver,
                    message: message
                })
            });

            console.log('Send message response:', response);
            await handleResponse(response, 'Message Sent');
            clearForm(e.target);
        } catch (error) {
            console.error('Send message error:', error);
            showAlert('Error: ' + error.message, false);
        }
    });

    // Read Messages Form Handler
    document.getElementById('readMessagesForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('readUsername').value;
        const password = document.getElementById('readPassword').value;

        console.log('Reading messages for:', { username, password });

        try {
            const response = await fetch(`/message?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`);

            console.log('Read messages response:', response);

            if (response.ok) {
                const messages = await response.json();
                displayMessages(messages);
                clearForm(e.target);
                showAlert('Messages loaded successfully!', true);
            } else {
                const errorText = await response.text();
                showAlert(`Error ${response.status}: ${errorText}`, false);
            }
        } catch (error) {
            console.error('Read messages error:', error);
            showAlert('Error: ' + error.message, false);
        }
    });
});