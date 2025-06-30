

// Redirect to login page if not logged in
if (!localStorage.getItem("isLoggedIn")) {
  window.location.href = "login.html";
}


// Script To Change Theme Color (Dark/Light)

document.addEventListener("DOMContentLoaded", () => {
  const toggleBtn = document.getElementById("themeToggle");

  toggleBtn.addEventListener("click", () => {
    document.body.classList.toggle("dark-mode");
    toggleBtn.textContent = document.body.classList.contains("dark-mode") 
      ? "Light Mode" 
      : "Dark Mode";
  });
});


// Script To Connect With Post Mapping API TO Store Data in DB
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("complaintForm");

  form.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = {
      title: form.title.value,
      description: form.description.value,
      category: form.category.value,
      status: form.status.value,
      submittedBy: form.submittedBy.value,
      deleted: false
    };

    fetch("http://localhost:8082/api/add-complaint", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(formData)
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to submit complaint");
        }
        return response.text(); // Use text() if backend returns plain text
      })
      .then((data) => {
        alert("✅ Complaint submitted successfully!");
        console.log("Response:", data);
        form.reset();
      })
      .catch((error) => {
        console.error("❌ Error submitting complaint:", error);
        alert("❌ Error: Complaint not submitted.");
      });
  });
});


// =============== Filter (Status/category/keyword) + Pagination + See All Records API Uses

// ============== Fetch Complaints API Function ===============
document.addEventListener("DOMContentLoaded", function () {
  const pageSize = 5;
  let currentPage = 0;

  const complaintsBody = document.getElementById("complaintsBody");
  const filterStatus = document.getElementById("filterStatus");
  const filterCategory = document.getElementById("filterCategory");
  const searchKeyword = document.getElementById("searchKeyword");
  const loadMoreBtn = document.getElementById("loadAllComplaintsBtn");
  const showLessBtn = document.getElementById("showLessBtn");

  function fetchComplaints(page = 0) {
    const status = filterStatus.value || "";
    const category = filterCategory.value || "";
    const keyword = searchKeyword.value || "";

    const apiUrl = `http://localhost:8082/api/pagination/filter?status=${status}&category=${category}&keyword=${keyword}&start=${page}&size=${pageSize}&sortBy=id`;

    console.log("📡 API URL:", apiUrl);

    fetch(apiUrl)
      .then(response => {
        if (!response.ok) throw new Error("API request failed");
        return response.json();
      })
      .then(data => {
        if (page === 0) complaintsBody.innerHTML = "";

        if (data.content && data.content.length > 0) {
          data.content.forEach((complaint) => {
            const row = document.createElement("tr");
            row.innerHTML = `
              <td>${complaint.id}</td>
              <td>${complaint.title}</td>
              <td>${complaint.description}</td>
              <td>${complaint.category}</td>
              <td>${complaint.status}</td>
              <td>${complaint.submittedBy}</td>
            `;
            complaintsBody.appendChild(row);
          });

          loadMoreBtn.style.display = data.last ? "none" : "inline-block";
          showLessBtn.style.display = page > 0 ? "inline-block" : "none";
          currentPage = page;
        } else {
          complaintsBody.innerHTML = "<tr><td colspan='6'>No complaints found.</td></tr>";
          loadMoreBtn.style.display = "none";
          showLessBtn.style.display = "none";
        }
      })
      .catch(error => {
        console.error("❌ Error fetching complaints:", error);
        complaintsBody.innerHTML = "<tr><td colspan='6'>Error loading data.</td></tr>";
      });
  }

  function applyFilters() {
    currentPage = 0;
    fetchComplaints(0);
  }

  function resetFilters() {
    filterStatus.value = "";
    filterCategory.value = "";
    searchKeyword.value = "";
    fetchComplaints(0);
  }

  loadMoreBtn.addEventListener("click", () => {
    fetchComplaints(currentPage + 1);
  });

  showLessBtn.addEventListener("click", () => {
    currentPage = 0;
    fetchComplaints(0);
  });

  // Initial fetch
  fetchComplaints(0);
});


//  LogOut Login

// Logout functionality
document.getElementById("logout-btn").addEventListener("click", function (e) {
  e.preventDefault();

  const confirmLogout = confirm("Are you sure you want to logout?");
  if (confirmLogout) {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("email");

    // Optional: Show a message or redirect immediately
    window.location.href = "login.html";
  }
});
