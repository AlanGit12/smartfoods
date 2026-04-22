<script>
    import { enhance } from "$app/forms";

    let { data } = $props();
    let { items, locations } = data;

    let showModal = $state(false);
    let selectedLocation = $state("");
    let selectedUnit = $state("PIECE");

    const units = ["PIECE", "GRAM", "KILOGRAM", "MILLILITER", "LITER"];

    const statusColor = {
        ACTIVE: "#3B6D11",
        CONSUMED: "#888",
        WASTED: "#c0392b",
    };

    const statusLabel = {
        ACTIVE: "Aktiv",
        CONSUMED: "Verbraucht",
        WASTED: "Entsorgt",
    };

    let filteredItems = $derived(
        selectedLocation
            ? items.filter((i) => i.storageLocationId === selectedLocation)
            : items,
    );

    function getLocationName(id) {
        return locations.find((l) => l.id === id)?.name ?? "—";
    }

    function formatDate(d) {
        if (!d) return "—";
        return new Date(d).toLocaleDateString("de-CH");
    }
</script>

<div class="page">
    <div class="page-header">
        <div>
            <h1 class="page-title">Inventar</h1>
            <p class="page-sub">Deine Lebensmittel im Überblick</p>
        </div>
        <button class="btn-primary" onclick={() => (showModal = true)}>
            + Hinzufügen
        </button>
    </div>

    <!-- Filter -->
    <div class="filter-bar">
        <button
            class="filter-chip"
            class:active={selectedLocation === ""}
            onclick={() => (selectedLocation = "")}
        >
            Alle
        </button>
        {#each locations as loc}
            <button
                class="filter-chip"
                class:active={selectedLocation === loc.id}
                onclick={() => (selectedLocation = loc.id)}
            >
                {loc.icon}
                {loc.name}
            </button>
        {/each}
    </div>

    <!-- Items Grid -->
    {#if filteredItems.length === 0}
        <div class="empty">
            <div class="empty-icon">🥦</div>
            <p>Noch keine Einträge vorhanden</p>
            <button class="btn-primary" onclick={() => (showModal = true)}
                >Erstes Item hinzufügen</button
            >
        </div>
    {:else}
        <div class="grid">
            {#each filteredItems as item}
                <div class="card" class:consumed={item.status !== "ACTIVE"}>
                    <div class="card-top">
                        <span class="item-name">{item.name}</span>
                        <span
                            class="status-badge"
                            style="background:{statusColor[
                                item.status
                            ]}22; color:{statusColor[item.status]}"
                        >
                            {statusLabel[item.status]}
                        </span>
                    </div>

                    <div class="card-meta">
                        <span>📍 {getLocationName(item.storageLocationId)}</span
                        >
                        <span>📅 {formatDate(item.expiryDate)}</span>
                    </div>

                    {#if item.unit !== "PIECE"}
                        <div class="amount-bar">
                            <div
                                class="amount-fill"
                                style="width:{Math.max(
                                    0,
                                    (item.remainingAmount / item.totalAmount) *
                                        100,
                                )}%"
                            ></div>
                        </div>
                        <div class="amount-text">
                            {item.remainingAmount} / {item.totalAmount}
                            {item.unit.toLowerCase()}
                        </div>
                    {/if}

                    {#if item.status === "ACTIVE"}
                        <div class="card-actions">
                            <form
                                method="POST"
                                action="?/consumeItem"
                                use:enhance
                            >
                                <input
                                    type="hidden"
                                    name="id"
                                    value={item.id}
                                />
                                {#if item.unit !== "PIECE"}
                                    <input
                                        type="number"
                                        name="amount"
                                        placeholder="Menge"
                                        class="amount-input"
                                        step="0.1"
                                        min="0.1"
                                    />
                                {:else}
                                    <input
                                        type="hidden"
                                        name="amount"
                                        value="1"
                                    />
                                {/if}
                                <button type="submit" class="btn-consume"
                                    >Konsumieren</button
                                >
                            </form>
                            <form
                                method="POST"
                                action="?/wasteItem"
                                use:enhance
                            >
                                <input
                                    type="hidden"
                                    name="id"
                                    value={item.id}
                                />
                                <button type="submit" class="btn-waste"
                                    >Entsorgen</button
                                >
                            </form>
                        </div>
                    {/if}
                </div>
            {/each}
        </div>
    {/if}
</div>

<!-- Modal -->
{#if showModal}
    <div class="overlay" onclick={() => (showModal = false)}>
        <div class="modal" onclick={(e) => e.stopPropagation()}>
            <div class="modal-header">
                <h2>Neues Item</h2>
                <button class="close-btn" onclick={() => (showModal = false)}
                    >✕</button
                >
            </div>
            <form
                method="POST"
                action="?/createItem"
                use:enhance={() => {
                    return ({ result }) => {
                        if (result.type === "success") showModal = false;
                    };
                }}
            >
                <div class="form-group">
                    <label>Name</label>
                    <input
                        type="text"
                        name="name"
                        required
                        placeholder="z.B. Milch"
                    />
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Einheit</label>
                        <select name="unit" bind:value={selectedUnit}>
                            {#each units as u}
                                <option value={u}>{u}</option>
                            {/each}
                        </select>
                    </div>
                    {#if selectedUnit !== "PIECE"}
                        <div class="form-group">
                            <label>Menge</label>
                            <input
                                type="number"
                                name="totalAmount"
                                step="0.1"
                                min="0"
                                placeholder="500"
                            />
                        </div>
                    {/if}
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Anzahl</label>
                        <input
                            type="number"
                            name="quantity"
                            min="1"
                            value="1"
                        />
                    </div>
                    <div class="form-group">
                        <label>Preis (CHF)</label>
                        <input
                            type="number"
                            name="purchasePrice"
                            step="0.05"
                            min="0"
                            placeholder="2.50"
                        />
                    </div>
                </div>
                <div class="form-group">
                    <label>Ablaufdatum</label>
                    <input type="date" name="expiryDate" required />
                </div>
                <div class="form-group">
                    <label>Lagerort</label>
                    <select name="storageLocationId" required>
                        <option value="">Bitte wählen...</option>
                        {#each locations as loc}
                            <option value={loc.id}>{loc.icon} {loc.name}</option
                            >
                        {/each}
                    </select>
                </div>
                <div class="modal-footer">
                    <button
                        type="button"
                        class="btn-secondary"
                        onclick={() => (showModal = false)}>Abbrechen</button
                    >
                    <button type="submit" class="btn-primary">Hinzufügen</button
                    >
                </div>
            </form>
        </div>
    </div>
{/if}

<style>
    .page {
        padding: 2rem;
        max-width: 1100px;
        margin: 0 auto;
    }

    .page-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 1.5rem;
    }
    .page-title {
        font-size: 1.8rem;
        font-weight: 700;
        color: #1a1a1a;
        margin: 0;
    }
    .page-sub {
        color: #888;
        margin: 0.2rem 0 0;
        font-size: 0.9rem;
    }

    .filter-bar {
        display: flex;
        gap: 0.5rem;
        flex-wrap: wrap;
        margin-bottom: 1.5rem;
    }
    .filter-chip {
        padding: 0.4rem 1rem;
        border-radius: 999px;
        border: 1px solid #ddd;
        background: white;
        cursor: pointer;
        font-size: 0.85rem;
        color: #555;
        transition: all 0.15s;
    }
    .filter-chip:hover {
        border-color: #3b6d11;
        color: #3b6d11;
    }
    .filter-chip.active {
        background: #3b6d11;
        color: white;
        border-color: #3b6d11;
    }

    .grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: 1rem;
    }

    .card {
        background: white;
        border-radius: 12px;
        padding: 1.2rem;
        border: 1px solid #eee;
        box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
        transition: box-shadow 0.2s;
    }
    .card:hover {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    }
    .card.consumed {
        opacity: 0.6;
    }

    .card-top {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 0.6rem;
    }
    .item-name {
        font-weight: 600;
        font-size: 1rem;
        color: #1a1a1a;
    }
    .status-badge {
        font-size: 0.75rem;
        padding: 0.2rem 0.6rem;
        border-radius: 999px;
        font-weight: 500;
    }

    .card-meta {
        display: flex;
        gap: 1rem;
        font-size: 0.82rem;
        color: #888;
        margin-bottom: 0.8rem;
    }

    .amount-bar {
        height: 6px;
        background: #eee;
        border-radius: 3px;
        overflow: hidden;
        margin-bottom: 0.3rem;
    }
    .amount-fill {
        height: 100%;
        background: #3b6d11;
        border-radius: 3px;
        transition: width 0.3s;
    }
    .amount-text {
        font-size: 0.8rem;
        color: #888;
        margin-bottom: 0.8rem;
    }

    .card-actions {
        display: flex;
        gap: 0.5rem;
        flex-wrap: wrap;
    }
    .card-actions form {
        display: flex;
        gap: 0.4rem;
        align-items: center;
    }

    .amount-input {
        width: 70px;
        padding: 0.3rem 0.5rem;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 0.8rem;
    }

    .btn-primary {
        background: #3b6d11;
        color: white;
        border: none;
        padding: 0.6rem 1.2rem;
        border-radius: 8px;
        cursor: pointer;
        font-size: 0.9rem;
        font-weight: 500;
        transition: background 0.15s;
    }
    .btn-primary:hover {
        background: #2d5509;
    }

    .btn-secondary {
        background: white;
        color: #555;
        border: 1px solid #ddd;
        padding: 0.6rem 1.2rem;
        border-radius: 8px;
        cursor: pointer;
        font-size: 0.9rem;
    }

    .btn-consume {
        background: #eaf3de;
        color: #3b6d11;
        border: 1px solid #3b6d11;
        padding: 0.35rem 0.8rem;
        border-radius: 6px;
        cursor: pointer;
        font-size: 0.8rem;
        font-weight: 500;
    }
    .btn-consume:hover {
        background: #3b6d11;
        color: white;
    }

    .btn-waste {
        background: #fdecea;
        color: #c0392b;
        border: 1px solid #c0392b;
        padding: 0.35rem 0.8rem;
        border-radius: 6px;
        cursor: pointer;
        font-size: 0.8rem;
    }
    .btn-waste:hover {
        background: #c0392b;
        color: white;
    }

    .empty {
        text-align: center;
        padding: 4rem 2rem;
        color: #888;
    }
    .empty-icon {
        font-size: 3rem;
        margin-bottom: 1rem;
    }

    /* Modal */
    .overlay {
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.4);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 100;
    }
    .modal {
        background: white;
        border-radius: 16px;
        padding: 1.5rem;
        width: 100%;
        max-width: 480px;
        max-height: 90vh;
        overflow-y: auto;
    }
    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.2rem;
    }
    .modal-header h2 {
        margin: 0;
        font-size: 1.2rem;
        color: #1a1a1a;
    }
    .close-btn {
        background: none;
        border: none;
        font-size: 1.1rem;
        cursor: pointer;
        color: #888;
        padding: 0.2rem 0.5rem;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        gap: 0.4rem;
        margin-bottom: 1rem;
    }
    .form-group label {
        font-size: 0.85rem;
        font-weight: 500;
        color: #444;
    }
    .form-group input,
    .form-group select {
        padding: 0.6rem 0.8rem;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 0.9rem;
        outline: none;
        transition: border-color 0.15s;
    }
    .form-group input:focus,
    .form-group select:focus {
        border-color: #3b6d11;
    }

    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 0.8rem;
    }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 0.8rem;
        margin-top: 1.5rem;
        padding-top: 1rem;
        border-top: 1px solid #eee;
    }
</style>
