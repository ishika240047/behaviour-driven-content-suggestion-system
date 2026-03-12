async function getSuggestion() {
    try {
        const response = await fetch("http://localhost:7070/suggest");
        const data = await response.text();

        document.getElementById("suggestion").innerText = data;
    } catch (error) {
        console.error("Error:", error);
    }
}