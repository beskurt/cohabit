<script lang="ts">
	import HeaderFooter from '$lib/HeaderFooter.svelte';

	let name = ''; // Not used in DTOs unless you include it later
	let email = '';
	let password = '';

	let street = '';
	let city = '';
	let postalCode = '';
	let country = '';

	let error = '';
	let success = '';

	async function handleRegister() {
		error = '';
		success = '';

		const userDTO = {
			email,
			password,
			address: {
				street,
				city,
				postalCode,
				country
			}
		};

		try {
			const res = await fetch('http://localhost:8080/v1/register', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(userDTO)
			});

			if (res.ok) {
				success = 'Registration successful!';
				// Optionally reset form fields
				name = email = password = street = city = postalCode = country = '';
			} else {
				const message = await res.text();
				error = `Registration failed: ${message}`;
			}
		} catch (e) {
			error = 'Unexpected error occurred. Please try again later.';
			console.error(e);
		}
	}
</script>

<section class="form-section">
	<h2>Register</h2>
	<form on:submit|preventDefault={handleRegister}>
		<label for="name">Full Name</label>
		<input id="name" type="text" bind:value={name} required />

		<label for="email">Email</label>
		<input id="email" type="email" bind:value={email} required />

		<label for="password">Password</label>
		<input id="password" type="password" bind:value={password} required />

		<h3>Address</h3>
		<label for="street">Street</label>
		<input id="street" type="text" bind:value={street} required />

		<label for="city">City</label>
		<input id="city" type="text" bind:value={city} required />

		<label for="postalCode">Postal Code</label>
		<input id="postalCode" type="text" bind:value={postalCode} required />

		<label for="country">Country</label>
		<input id="country" type="text" bind:value={country} required />

		<button type="submit">Register</button>
	</form>

	{#if error}
		<p style="color: red;">{error}</p>
	{/if}
	{#if success}
		<p style="color: green;">{success}</p>
	{/if}
</section>
