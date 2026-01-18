<script lang="ts">
	let title = '';
	let description = '';
	let categoryName = '';
	let condition = '';
	let price = 0;
	let currency = '';

	// Replace image file upload with manual URL entry
	// let imageUrls: string[] = ['']; // start with one empty field

	// function addImageField() {
	// 	if (imageUrls.length < 10) {
	// 		imageUrls = [...imageUrls, ''];
	// 	}
	// }
	//
	// function removeImageField(index: number) {
	// 	imageUrls = imageUrls.filter((_, i) => i !== index);
	// }

	async function handleSubmit() {
		const token = localStorage.getItem('auth_token');
		if (!token) {
			alert('Please log in first.');
			window.location.href = '/login';
			return;
		}

		// const validUrls = imageUrls.map((url, i) => url.trim()).filter(Boolean);
		// if (validUrls.length === 0) {
		// 	alert('Please enter at least one image URL.');
		// 	return;
		// }
		//
		// if (validUrls.length > 10) {
		// 	alert('You can enter a maximum of 10 images.');
		// 	return;
		// }

		// const images = validUrls.map((url, i) => ({
		// 	url,
		// 	displayOrder: i
		// }));

		const listingDTO = {
			title,
			description,
			category: { name: categoryName },
			condition,
			price: { amount: price, currency: currency },
		};

		try {
			const res = await fetch('http://localhost:8081/v1/listings', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				},
				body: JSON.stringify(listingDTO)
			});

			if (res.status === 401) {
				alert('Session expired – please log in again.');
				localStorage.removeItem('auth_token');
				window.location.href = '/login';
				return;
			}

			if (!res.ok) {
				const msg = await res.text();
				throw new Error(`Failed to create listing (${res.status}): ${msg}`);
			}

			alert('Listing created successfully!');
			window.location.href = '/browse';
		} catch (err) {
			console.error(err);
			alert(`Failed to create listing: ${err.message}`);
		}
	}
</script>


<section class="form-section">
	<h2>Create a New Listing</h2>
	<form on:submit|preventDefault={handleSubmit}>
		<label for="title">Title</label>
		<input id="title" type="text" bind:value={title} maxlength="100" required />

		<label for="description">Description</label>
		<textarea id="description" bind:value={description} maxlength="1000" required></textarea>

		<label for="category">Category</label>
		<input id="category" type="text" bind:value={categoryName} required />

		<label for="condition">Condition</label>
		<select id="condition" bind:value={condition} required>
			<option value="">Select Condition</option>
			<option value="NEW">New</option>
			<option value="USED">Used</option>
		</select>

		<label for="currency">Price</label>
		<select id="currency" bind:value={currency} required>
			<option value="EUR">EUR</option>
			<option value="USD">United States Dollar</option>
			<option value="JPY">Japanese Yen</option>
			<option value="GPB">British Pound Sterling</option>
		</select>
		<input id="price" type="number" bind:value={price} step="0.50" min="0" required />

<!--		<label>Image URLs (1–10)</label>-->
<!--		{#each imageUrls as url, i}-->
<!--			<div style="display: flex; gap: 0.5rem; margin-bottom: 0.5rem;">-->
<!--				<input-->
<!--					type="url"-->
<!--					placeholder="https://example.com/image.jpg"-->
<!--					bind:value={imageUrls[i]}-->
<!--					required-->
<!--					style="flex: 1"-->
<!--				/>-->
<!--				{#if imageUrls.length > 1}-->
<!--					<button type="button" on:click={() => removeImageField(i)}>Remove</button>-->
<!--				{/if}-->
<!--			</div>-->
<!--		{/each}-->
<!--		<button type="button" on:click={addImageField} disabled={imageUrls.length >= 10}>+ Add Another Image</button>-->

		<button type="submit" style="margin-top: 1rem;">Submit Listing</button>
	</form>
</section>

