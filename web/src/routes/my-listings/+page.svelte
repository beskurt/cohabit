<script lang="ts">
	import HeaderFooter from '$lib/HeaderFooter.svelte';
	import { onMount } from 'svelte';

	let listings = [];
	let editingId: string | null = null;
	let editedItem: any = null;
	let error = '';

	onMount(async () => {
		const token = localStorage.getItem('auth_token');
		if (!token) {
			window.location.href = '/login';
			return;
		}

		try {
			const res = await fetch('http://localhost:8081/v1/listings/my', {
				headers: {
					Authorization: `Bearer ${localStorage.getItem('auth_token')}`
				}
			});

			if (res.status === 401) {
				localStorage.removeItem('auth_token');
				window.location.href = '/login';
				return;
			}

			if (res.ok) {
				listings = await res.json();
			} else {
				error = 'Failed to load listings';
			}
		} catch (err) {
			console.error(err);
			error = 'Something went wrong';
		}
	});

	async function deleteListing(id: string) {
		const token = localStorage.getItem('auth_token');
		if (!token) return;

		if (confirm('Are you sure you want to delete this listing?')) {
			await fetch(`http://localhost:8081/v1/listings/my/${id}`, {
				method: 'DELETE',
				headers: { Authorization: `Bearer ${token}` }
			});
			listings = listings.filter(l => l.id !== id);
		}
	}

	function startEditing(item) {
		editingId = item.id;
		editedItem = JSON.parse(JSON.stringify(item));
	}

	async function saveEdit(id: string) {
		const token = localStorage.getItem('auth_token');
		if (!token) return;

		const res = await fetch(`http://localhost:8081/v1/listings/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}`
			},
			body: JSON.stringify({
				title: editedItem.title,
				description: editedItem.description,
				category: editedItem.category,
				condition: editedItem.condition,
				price: editedItem.price,
				images: editedItem.images
			})
		});

		if (res.ok) {
			const updated = await res.json();
			listings = listings.map(l => l.id === id ? updated : l);
			editingId = null;
			editedItem = null;
		} else {
			alert('Failed to update listing');
		}
	}
</script>

<section class="form-section">
	<h2>My Listings</h2>

	{#if error}
		<p style="color: red;">{error}</p>
	{/if}

	{#if listings.length === 0}
		<p>No listings found.</p>
	{:else}
		<div class="featured">
			{#each listings as item (item.id)}
				<div class="item">
<!--					<img src={item.images[0]?.url || '/fallback.jpg'} alt={item.title} />-->
					<div class="item-details">
						{#if editingId === item.id}
							<input bind:value={editedItem.title} placeholder="Title" />
							<input bind:value={editedItem.price.amount} type="number" step="0.01" placeholder="Price" />
							<input bind:value={editedItem.price.currency} placeholder="Currency" />
							<textarea bind:value={editedItem.description} placeholder="Description" />
							<input bind:value={editedItem.condition} placeholder="Condition" />
							<input bind:value={editedItem.category.name} placeholder="Category name" />

							<button on:click={() => saveEdit(item.id)} style="margin-top: 0.5rem">Save</button>
						{:else}
							<h3>{item.title}</h3>
							<p>{item.price.amount.toFixed(2)} {item.price.currency}</p>
							<p>{item.condition}</p>

							<button on:click={() => startEditing(item)} style="margin-top: 0.5rem">Edit</button>
							<button on:click={() => deleteListing(item.id)} style="margin-top: 0.5rem">Delete</button>
						{/if}
					</div>
				</div>
			{/each}
		</div>
	{/if}
</section>
