document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('registerForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('regUsername').value;
        const password = document.getElementById('regPassword').value;

        try {
            const response = await fetch('/user', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    username: username,
                    password: password
                })
            });

            handleResponse(response, 'Registration');
            clearForm(e.target);
        } catch (error) {
            showAlert('Error: ' + error.message, false);
        }
    });

    document.getElementById('sendMessageForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const receiver = document.getElementById('receiverUsername').value;
        const message = document.getElementById('messageContent').value;

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

            handleResponse(response, 'Message Sent');
            clearForm(e.target);
        } catch (error) {
            showAlert('Error: ' + error.message, false);
        }
    });

    document.getElementById('readMessagesForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('readUsername').value;
        const password = document.getElementById('readPassword').value;

        try {
            const response = await fetch(`/message?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`);

            if (response.ok) {
                const messages = await response.json();
                displayMessages(messages);
                clearForm(e.target);
                showAlert('Messages loaded successfully!', true);
            } else {
                const error = await response.text();
                showAlert(`Error ${response.status}: ${error}`, false);
            }
        } catch (error) {
            showAlert('Error: ' + error.message, false);
        }
    });
});

function handleResponse(response, action) {
    if (response.ok) {
        showAlert(`${action} successful!`, true);
    } else {
        response.text().then(error => showAlert(`${action} failed: ${error}`, false));
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

function displayMessages(messages) {
    const container = document.getElementById('messagesContainer');
    container.innerHTML = '';

    if (messages.length === 0) {
        container.innerHTML = '<div class="message">No messages found</div>';
        return;
    }

    messages.forEach(msg => {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'message';
        messageDiv.innerHTML = `
            <div><strong>${msg.timestamp}</strong></div>
            <div>${msg.content}</div>
        `;
        container.appendChild(messageDiv);
    });
}