<script>
    import { enhance } from '$app/forms';

    let { data } = $props();
    let { locations } = data;

    let showModal = $state(false);

    const storageTypes = [
        { value: 'COOLED', label: 'Gekühlt', icon: '❄️' },
        { value: 'FROZEN', label: 'Gefroren', icon: '🧊' },
        { value: 'ROOM_TEMPERATURE', label: 'Raumtemperatur', icon: '🌡️' }
    ];

    const suggestedIcons = ['🧊', '❄️', '🏠', '🍷', '🥩', '🥦', '🧴', '🫙'];

    const typeColor = {
        COOLED: { bg: '#e8f4fd', color: '#1a6fa6' },
        FROZEN: { bg: '#e8eeff', color: '#3a4db5' },
        ROOM_TEMPERATURE: { bg: '#fdf3e8', color: '#a66a1a' }
    };

    const typeLabel = {
        COOLED: 'Gekühlt',
        FROZEN: 'Gefroren',
        ROOM_TEMPERATURE: 'Raumtemperatur'
    };
</script>

<div class="page">
    <div class="page-header">
        <div>
            <h1 class="page-title">Lagerorte</h1>
            <p class="page-sub">Verwalte deine Kühlschränke, Gefrierfächer und mehr</p>
        </div>
        <button class="btn-primary" onclick={() => showModal = true}>
            + Neuer Lagerort
        </button>
    </div>

    {#if locations.length === 0}
        <div class="empty">
            <div class="empty-icon">📦</div>
            <p>Noch keine Lagerorte vorhanden</p>
            <button class="btn-primary" onclick={() => showModal = true}>Ersten Lagerort hinzufügen</button>
        </div>
    {:else}
        <div class="grid">
            {#each locations as loc}
                <div class="card">
                    <div class="card-icon">{loc.icon}</div>
                    <div class="card-body">
                        <div class="card-name">{loc.name}</div>
                        <span
                            class="type-badge"
                            style="background:{typeColor[loc.storageType]?.bg}; color:{typeColor[loc.storageType]?.color}"
                        >
                            {typeLabel[loc.storageType]}
                        </span>
                    </div>
                    <form method="POST" action="?/deleteLocation" use:enhance>
                        <input type="hidden" name="id" value={loc.id} />
                        <button type="submit" class="delete-btn" title="Löschen">✕</button>
                    </form>
                </div>
            {/each}
        </div>
    {/if}
</div>

<!-- Modal -->
{#if showModal}
    <div
        class="overlay"
        onclick={() => showModal = false}
        onkeydown={(e) => e.key === 'Escape' && (showModal = false)}
        role="button"
        tabindex="0"
    >
        <div class="modal" onclick={(e) => e.stopPropagation()}>
            <div class="modal-header">
                <h2>Neuer Lagerort</h2>
                <button class="close-btn" onclick={() => showModal = false}>✕</button>
            </div>
            <form
                method="POST"
                action="?/createLocation"
                use:enhance={() => {
                    return ({ result }) => {
                        if (result.type === 'success') showModal = false;
                    };
                }}
            >
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" name="name" required placeholder="z.B. Kühlschrank" />
                </div>

                <div class="form-group">
                    <label>Typ</label>
                    <select name="storageType" required>
                        {#each storageTypes as type}
                            <option value={type.value}>{type.icon} {type.label}</option>
                        {/each}
                    </select>
                </div>

                <div class="form-group">
                    <label>Icon</label>
                    <div class="icon-picker">
                        {#each suggestedIcons as icon}
                            <label class="icon-option">
                                <input type="radio" name="icon" value={icon} />
                                <span class="icon-btn">{icon}</span>
                            </label>
                        {/each}
                    </div>
                    <input type="text" name="icon" placeholder="Oder eigenes Emoji eingeben" class="icon-input" />
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn-secondary" onclick={() => showModal = false}>Abbrechen</button>
                    <button type="submit" class="btn-primary">Erstellen</button>
                </div>
            </form>
        </div>
    </div>
{/if}

<style>
    .page { padding: 2rem; max-width: 1100px; margin: 0 auto; }

    .page-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 2rem;
    }
    .page-title { font-size: 1.8rem; font-weight: 700; color: #1a1a1a; margin: 0; }
    .page-sub { color: #888; margin: 0.2rem 0 0; font-size: 0.9rem; }

    .grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
        gap: 1rem;
    }

    .card {
        background: white;
        border-radius: 12px;
        padding: 1.2rem;
        border: 1px solid #eee;
        box-shadow: 0 1px 4px rgba(0,0,0,0.05);
        display: flex;
        align-items: center;
        gap: 1rem;
        transition: box-shadow 0.2s;
    }
    .card:hover { box-shadow: 0 4px 12px rgba(0,0,0,0.08); }

    .card-icon { font-size: 1.5rem; }
    .card-body { flex: 1; }
    .card-name { font-weight: 600; color: #1a1a1a; margin-bottom: 0.3rem; }

    .type-badge {
        font-size: 0.75rem;
        padding: 0.2rem 0.6rem;
        border-radius: 999px;
        font-weight: 500;
    }

    .delete-btn {
        background: none;
        border: none;
        color: #ccc;
        cursor: pointer;
        font-size: 1rem;
        padding: 0.3rem;
        border-radius: 4px;
        transition: color 0.15s, background 0.15s;
    }
    .delete-btn:hover { color: #c0392b; background: #fdecea; }

    .empty {
        text-align: center;
        padding: 4rem 2rem;
        color: #888;
    }
    .empty-icon { font-size: 3rem; margin-bottom: 1rem; }

    .btn-primary {
        background: #3B6D11;
        color: white;
        border: none;
        padding: 0.6rem 1.2rem;
        border-radius: 8px;
        cursor: pointer;
        font-size: 0.9rem;
        font-weight: 500;
        transition: background 0.15s;
    }
    .btn-primary:hover { background: #2d5509; }

    .btn-secondary {
        background: white;
        color: #555;
        border: 1px solid #ddd;
        padding: 0.6rem 1.2rem;
        border-radius: 8px;
        cursor: pointer;
        font-size: 0.9rem;
    }

    .overlay {
        position: fixed;
        inset: 0;
        background: rgba(0,0,0,0.4);
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
        max-width: 420px;
    }
    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.2rem;
    }
    .modal-header h2 { margin: 0; font-size: 1.2rem; color: #1a1a1a; }
    .close-btn {
        background: none;
        border: none;
        font-size: 1.1rem;
        cursor: pointer;
        color: #888;
    }

    .form-group { display: flex; flex-direction: column; gap: 0.4rem; margin-bottom: 1rem; }
    .form-group label { font-size: 0.85rem; font-weight: 500; color: #444; }
    .form-group input, .form-group select {
        padding: 0.6rem 0.8rem;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 0.9rem;
        outline: none;
        transition: border-color 0.15s;
    }
    .form-group input:focus, .form-group select:focus { border-color: #3B6D11; }

    .icon-picker {
        display: flex;
        gap: 0.5rem;
        flex-wrap: wrap;
        margin-bottom: 0.5rem;
    }
    .icon-option input { display: none; }
    .icon-btn {
        font-size: 1.5rem;
        padding: 0.3rem;
        border-radius: 8px;
        cursor: pointer;
        border: 2px solid transparent;
        display: block;
        transition: border-color 0.15s;
    }
    .icon-option input:checked + .icon-btn { border-color: #3B6D11; }
    .icon-btn:hover { border-color: #aaa; }
    .icon-input { margin-top: 0.3rem; }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 0.8rem;
        margin-top: 1.5rem;
        padding-top: 1rem;
        border-top: 1px solid #eee;
    }
</style>
