// Passsword show/Hide

  function togglePassword(inputId, iconElement) {
    const input = document.getElementById(inputId);
    const isPassword = input.type === "password";

    input.type = isPassword ? "text" : "password";
    iconElement.setAttribute("name", isPassword ? "eye" : "eye-off");
  }


//   API Connection =============

document.getElementById("registerForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const name = document.querySelector("input[name='name']").value.trim();
  const email = document.querySelector("input[name='email']").value.trim(); // using username as email field
  const password = document.querySelector("input[name='password']").value.trim();
  const confirmPassword = document.querySelector("input[name='confirmPassword']").value.trim();

  const messageBox = document.getElementById("register-message") || document.createElement("p");
  messageBox.id = "register-message";
  messageBox.style.marginTop = "10px";
  this.appendChild(messageBox);

  // ✅ Password match check
  if (password !== confirmPassword) {
    messageBox.style.color = "red";
    messageBox.textContent = "❌ Passwords do not match.";
    return;
  }

  try {
    const response = await fetch("http://localhost:8082/api/register/user", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name,
        email,
        password
      })
    });

    if (response.ok) {
      messageBox.style.color = "green";
      messageBox.textContent = "✅ Registered successfully! Redirecting to login...";

      setTimeout(() => {
        window.location.href = "login.html";
      }, 1500);
    } else {
      const result = await response.text();
      messageBox.style.color = "red";
      messageBox.textContent = result || "❌ Registration failed.";
    }
  } catch (error) {
    console.error("Registration error:", error);
    messageBox.style.color = "red";
    messageBox.textContent = "❌ Server error. Please try again later.";
  }
});
