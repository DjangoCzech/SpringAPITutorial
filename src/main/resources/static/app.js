const API_URL = "/api/students";

const reloadBtn = document.getElementById("reloadBtn");
const statusEl = document.getElementById("status");
const studentsBody = document.getElementById("studentsBody");

reloadBtn.addEventListener("click", loadStudents);

async function loadStudents() {
    setLoadingState(true, "Nacitam data...");

    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error("API vratilo chybu " + response.status);
        }

        const students = await response.json();
        renderStudents(students);

        const countText = students.length === 1 ? "1 student" : students.length + " studenti";
        setStatus("Nacteno: " + countText, false);
    } catch (error) {
        studentsBody.innerHTML =
            '<tr><td colspan="5" class="empty">Nepodarilo se nacist data. Zkontroluj, ze bezi backend.</td></tr>';
        setStatus(error.message, true);
    } finally {
        setLoadingState(false);
    }
}

function renderStudents(students) {
    if (!Array.isArray(students) || students.length === 0) {
        studentsBody.innerHTML = '<tr><td colspan="5" class="empty">Seznam je prazdny.</td></tr>';
        return;
    }

    studentsBody.innerHTML = students
        .map((student) => {
            return `
                <tr>
                    <td>${escapeHtml(student.id)}</td>
                    <td>${escapeHtml(student.firstName)}</td>
                    <td>${escapeHtml(student.lastName)}</td>
                    <td>${escapeHtml(student.email)}</td>
                    <td>${escapeHtml(student.studyYear)}</td>
                </tr>
            `;
        })
        .join("");
}

function setLoadingState(isLoading, message) {
    reloadBtn.disabled = isLoading;
    if (message) {
        setStatus(message, false);
    }
}

function setStatus(message, isError) {
    statusEl.textContent = message;
    statusEl.classList.toggle("error", Boolean(isError));
}

function escapeHtml(value) {
    const safe = value === undefined || value === null ? "" : String(value);
    return safe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/\"/g, "&quot;")
        .replace(/'/g, "&#39;");
}
