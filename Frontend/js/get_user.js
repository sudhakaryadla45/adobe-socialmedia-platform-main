// const id = parseInt(document.getElementById("user-id"));

console.log((document.getElementById("user-id")).value);
// Define the API endpoint URL
const apiUrl = `http://localhost:8181/users`;

// Fetch the user profile data from the API
fetch(apiUrl)
	.then(response => response.json())
	.then(data => {
		// Get the div element where we will display the user profile data
		const userProfileDiv = document.getElementById("user-profile");

		// Create a new HTML element to display the user profile data
		const userProfile = document.createElement("div");

		// Set the innerHTML of the userProfile element to display the user profile data
		userProfile.innerHTML = `
			<p><strong>Name:</strong> ${data.name}</p>
			<p><strong>Email:</strong> ${data.email}</p>
			<p><strong>Bio:</strong> ${data.bio}</p>
			<p><strong>Created At:</strong> ${data.createdAt}</p>
			<p><strong>Updated At:</strong> ${data.updatedAt}</p>
		`;

		// Append the userProfile element to the userProfileDiv element
		userProfileDiv.appendChild(userProfile);
	})
	.catch(error => console.log(error));
