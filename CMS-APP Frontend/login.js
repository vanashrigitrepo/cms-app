document.getElementById("login").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.querySelector("input[name='email']").value.trim();
  const password = document.querySelector("input[name='password']").value.trim();

  const messageBox = document.getElementById("login-message") || document.createElement("p");
  messageBox.id = "login-message";
  messageBox.style.marginTop = "10px";
  this.appendChild(messageBox);

  try {
    const response = await fetch("http://localhost:8082/api/user/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email, password })
    });

    const result = await response.json();

    if (response.ok && result.success) {
      messageBox.style.color = "green";
      messageBox.textContent = result.message || "✅ Login successful!";
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("email", result.email);
      setTimeout(() => {
        window.location.href = "complaints.html";
      }, 1500);
    } else {
      messageBox.style.color = "red";
      messageBox.textContent = result.message || "❌ Invalid credentials.";
    }
  } catch (error) {
    messageBox.style.color = "red";
    messageBox.textContent = "❌ Server error. Please try again later.";
    console.error("Login error:", error);
  }
});

