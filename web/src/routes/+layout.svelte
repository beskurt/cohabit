<script>
	import '../app.css';
	import HeaderFooter from '$lib/HeaderFooter.svelte';

	const user = 'ALBIN';
</script>

<HeaderFooter {user}>
	<slot />
</HeaderFooter>
