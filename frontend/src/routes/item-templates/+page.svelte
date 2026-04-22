<script>
    import { enhance } from '$app/forms';

    let { data } = $props();
    let { templates } = data;

    let showCreateModal = $state(false);
    let showEditModal = $state(false);
    let editTemplate = $state(null);

    const units = ['PIECE', 'GRAM', 'KILOGRAM', 'MILLILITER', 'LITER'];
    const unitLabel = {
        PIECE: 'Stück',
        GRAM: 'Gramm',
        KILOGRAM: 'Kilogramm',
        MILLILITER: 'Milliliter',
        LITER: 'Liter'
    };

    function openEdit(template) {
        editTemplate = { ...template };
        showEditModal = true;
    }
</script>

<div class="page">
    <div class="page-header">
        <div>
            <h1 class="page-title">Vorlagen</h1>
            <p class="page-sub">Produktvorlagen für schnelles Hinzufügen ins Inventar</p>
        </div>
        <button class="btn-primary" onclick={() => showCreateModal = true}>
            + Neue Vorlage
        </button>
    </div>

    {#if templates.length === 0}
        <div class="empty">
            <div class="empty-icon">📋</div>
            <p>Noch keine Vorlagen vorhanden</p>
            <button class="btn-primary" onclick={() => showCreateModal = true}>Erste Vorlage erstellen</button>
        </div>
    {:else}
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Einheit</th>
                        <th>Standardmenge</th>
                        <th>Standardpreis</th>
                        <th>Aktionen</th>
                    </tr>
                </thead>
                <tbody>
                    {#each templates as template}
                        <tr>
                            <td class="name-cell">{template.name}</td>
                            <td>
                                <span class="unit-badge">{unitLabel[template.defaultUnit]}</span>
                            </td>
                            <td>{template.defaultAmount} {template.defaultUnit.toLowerCase()}</td>
                            <td>CHF {template.defaultPrice?.toFixed(2)}</td>
                            <td class="actions-cell">
                                <button class="btn-edit" onclick={() => openEdit(template)}>
                                    Bearbeiten
                                </button>
                                <form method="POST" action="?/deleteTemplate" use:enhance style="display:inline">
                                    <input type="hidden" name="id" value={template.id} />
                                    <button type="submit" class="btn-delete">Löschen</button>
                                </form>
                            </td>
                        </tr>
                    {/each}
                </tbody>
            </table>
        </div>
    {/if}
</div>

<!-- Create Modal -->
{#if showCreateModal}
    <div
        class="overlay"
        onclick={() => showCreateModal = false}
        onkeydown={(e) => e.key === 'Escape' && (showCreateModal = false)}
        role="button"
        tabindex="0"
    >
        <div class="modal" onclick={(e) => e.stopPropagation()}>
            <div class="modal-header">
                <h2>Neue Vorlage</h2>
                <button class="close-btn" onclick={() => showCreateModal = false}>✕</button>
            </div>
            <form
                method="POST"
                action="?/createTemplate"
                use:enhance={() => {
                    return ({ result }) => {
                        if (result.type === 'success') showCreateModal = false;
                    };
                }}
            >
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" name="name" required placeholder="z.B. Vollmilch" />
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Einheit</label>
                        <select name="defaultUnit" required>
                            {#each units as u}
                                <option value={u}>{unitLabel[u]}</option>
                            {/each}
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Standardmenge</label>
                        <input type="number" name="defaultAmount" step="0.1" min="0" placeholder="1.0" required />
                    </div>
                </div>
                <div class="form-group">
                    <label>Standardpreis (CHF)</label>
                    <input type="number" name="defaultPrice" step="0.05" min="0" placeholder="1.90" required />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-secondary" onclick={() => showCreateModal = false}>Abbrechen</button>
                    <button type="submit" class="btn-primary">Erstellen</button>
                </div>
            </form>
        </div>
    </div>
{/if}

<!-- Edit Modal -->
{#if showEditModal && editTemplate}
    <div
        class="overlay"
        onclick={() => showEditModal = false}
        onkeydown={(e) => e.key === 'Escape' && (showEditModal = false)}
        role="button"
        tabindex="0"
    >
        <div class="modal" onclick={(e) => e.stopPropagation()}>
            <div class="modal-header">
                <h2>Vorlage bearbeiten</h2>
                <button class="close-btn" onclick={() => showEditModal = false}>✕</button>
            </div>
            <form
                method="POST"
                action="?/updateTemplate"
                use:enhance={() => {
                    return ({ result }) => {
                        if (result.type === 'success') showEditModal = false;
                    };
                }}
            >
                <input type="hidden" name="id" value={editTemplate.id} />
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" name="name" value={editTemplate.name} required />
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Einheit</label>
                        <select name="defaultUnit">
                            {#each units as u}
                                <option value={u} selected={editTemplate.defaultUnit === u}>{unitLabel[u]}</option>
                            {/each}
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Standardmenge</label>
                        <input type="number" name="defaultAmount" step="0.1" value={editTemplate.defaultAmount} required />
                    </div>
                </div>
                <div class="form-group">
                    <label>Standardpreis (CHF)</label>
                    <input type="number" name="defaultPrice" step="0.05" value={editTemplate.defaultPrice} required />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-secondary" onclick={() => showEditModal = false}>Abbrechen</button>
                    <button type="submit" class="btn-primary">Speichern</button>
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

    .table-wrapper {
        background: white;
        border-radius: 12px;
        border: 1px solid #eee;
        overflow: hidden;
        box-shadow: 0 1px 4px rgba(0,0,0,0.05);
    }

    table { width: 100%; border-collapse: collapse; }
    thead { background: #f8f9fa; }
    th {
        padding: 0.9rem 1.2rem;
        text-align: left;
        font-size: 0.8rem;
        font-weight: 600;
        color: #888;
        text-transform: uppercase;
        letter-spacing: 0.05em;
        border-bottom: 1px solid #eee;
    }
    td {
        padding: 0.9rem 1.2rem;
        font-size: 0.9rem;
        color: #333;
        border-bottom: 1px solid #f5f5f5;
    }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: #fafafa; }

    .name-cell { font-weight: 500; color: #1a1a1a; }

    .unit-badge {
        background: #EAF3DE;
        color: #3B6D11;
        padding: 0.2rem 0.6rem;
        border-radius: 999px;
        font-size: 0.78rem;
        font-weight: 500;
    }

    .actions-cell { display: flex; gap: 0.5rem; align-items: center; }

    .btn-edit {
        background: #f0f0f0;
        color: #444;
        border: none;
        padding: 0.35rem 0.8rem;
        border-radius: 6px;
        cursor: pointer;
        font-size: 0.82rem;
        transition: background 0.15s;
    }
    .btn-edit:hover { background: #e0e0e0; }

    .btn-delete {
        background: #fdecea;
        color: #c0392b;
        border: none;
        padding: 0.35rem 0.8rem;
        border-radius: 6px;
        cursor: pointer;
        font-size: 0.82rem;
        transition: background 0.15s;
    }
    .btn-delete:hover { background: #c0392b; color: white; }

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

    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 0.8rem; }

    .modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 0.8rem;
        margin-top: 1.5rem;
        padding-top: 1rem;
        border-top: 1px solid #eee;
    }
</style>
