const API_BASE_URL = 'http://localhost:8080/api/events';

// Dans scripts.js - fetchEvents
function fetchEvents() {
    fetch(API_BASE_URL) // GET /api/events
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(successResponse => { // successResponse est l'objet { message: "...", data: [...] }
            const eventsList = document.getElementById('events-list');
            if (successResponse && successResponse.data) { // ACCÉDER À successResponse.data
                eventsList.innerHTML = successResponse.data.map(event => `
                    <div class="event-card">
                        <h3>${event.nom}</h3>
                        <p>Date: ${new Date(event.date).toLocaleString()}</p>
                        <p>Lieu: ${event.lieu}</p>
                        <p>Capacité: ${event.capaciteMax}</p>
                        <p>Type: ${event.type}</p> {/* Assure-toi que 'type' est dans les données de l'événement */}
                        ${event.type === 'Conference' ? `
                            <p>Thème: ${event.theme || 'N/A'}</p>
                            // <p>Intervenants: ${event.intervenants && event.intervenants.length ? event.intervenants.join(', ') : 'Aucun'}</p> // Vérifie si 'intervenants' est fourni par le backend
                        ` : event.type === 'Concert' ? `
                            <p>Artiste: ${event.artiste || 'N/A'}</p>
                            <p>Genre: ${event.genreMusical || 'N/A'}</p>
                        ` : ''}
                        <a href="/event-details.html?id=${event.id}">Détails</a>
                        <button onclick="deleteEvent('${event.id}')">Supprimer</button>
                    </div>
                `).join('');
            } else {
                eventsList.innerHTML = '<p>Aucun événement trouvé ou format de réponse incorrect.</p>';
                console.warn('Format de réponse inattendu:', successResponse);
            }
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des événements:', error);
            const eventsList = document.getElementById('events-list');
            eventsList.innerHTML = '<p>Erreur lors du chargement des événements.</p>';
        });
}

// Dans scripts.js - fetchEventDetails
function fetchEventDetails(eventId) {
    fetch(`${API_BASE_URL}/${eventId}`) // GET /api/events/{eventId}
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(successResponse => { // successResponse est l'objet { message: "...", data: {...} }
            const details = document.getElementById('event-details');
            if (successResponse && successResponse.data) { // ACCÉDER À successResponse.data
                const event = successResponse.data;
                details.innerHTML = `
                    <h3>${event.nom}</h3>
                    <p>Date: ${new Date(event.date).toLocaleString()}</p>
                    <p>Lieu: ${event.lieu}</p>
                    <p>Capacité: ${event.capaciteMax}</p>
                    <p>Type: ${event.type}</p> {/* Assure-toi que 'type' est dans les données de l'événement */}
                     ${event.type === 'Conference' ? `
                        <p>Thème: ${event.theme || 'N/A'}</p>
                        // <p>Intervenants: ${event.intervenants && event.intervenants.length ? event.intervenants.join(', ') : 'Aucun'}</p> // Vérifie si 'intervenants' est fourni par le backend
                    ` : event.type === 'Concert' ? `
                        <p>Artiste: ${event.artiste || 'N/A'}</p>
                        <p>Genre: ${event.genreMusical || 'N/A'}</p>
                    ` : ''}
                `;
            } else {
                details.innerHTML = '<p>Détails de l\'événement non trouvés ou format de réponse incorrect.</p>';
            }
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des détails de l\'événement:', error);
            const details = document.getElementById('event-details');
            details.innerHTML = '<p>Erreur lors du chargement des détails de l\'événement.</p>';
        });
}

function deleteEvent(id) {
    if (confirm('Voulez-vous supprimer cet événement ?')) {
        fetch(`${API_BASE_URL}/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert('Événement supprimé');
                    fetchEvents();
                } else {
                    alert('Erreur lors de la suppression');
                }
            })
            .catch(error => console.error('Erreur:', error));
    }
}

function setupCreateEventForm() {
    const form = document.getElementById('create-event-form');
    const typeSelect = document.getElementById('type');
    const conferenceFields = document.getElementById('conference-fields');
    const concertFields = document.getElementById('concert-fields');
    const message = document.getElementById('form-message');

    typeSelect.addEventListener('change', () => {
        conferenceFields.style.display = typeSelect.value === 'Conference' ? 'block' : 'none';
        concertFields.style.display = typeSelect.value === 'Concert' ? 'block' : 'none';
    });

    // Dans scripts.js - setupCreateEventForm
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const type = formData.get('type'); // 'Conference' ou 'Concert'

        // Prépare l'objet de données pour le corps JSON
        // Note: tes méthodes backend attendent un 'organisateurId'
        // Tu devras l'ajouter au formulaire ou le récupérer d'une autre manière
        const eventData = {
            nom: formData.get('nom'),
            date: formData.get('date'), // Assure-toi que le format de date est compatible (LocalDateTime)
            lieu: formData.get('lieu'),
            capaciteMax: parseInt(formData.get('capaciteMax')),
            // organisateurId: formData.get('organisateurId'), // AJOUTER CE CHAMP AU FORMULAIRE HTML

            // Champs spécifiques au type
            theme: type === 'Conference' ? formData.get('theme') : undefined,
            // intervenants: type === 'Conference' && formData.get('intervenants') ? formData.get('intervenants').split(',').map(s => s.trim()) : undefined, // Non géré par le backend actuel pour la création
            artiste: type === 'Concert' ? formData.get('artiste') : undefined,
            genreMusical: type === 'Concert' ? formData.get('genreMusical') : undefined
        };

        // AJOUT: Récupérer l'organisateurId du formulaire
        const organisateurId = formData.get('organisateurId');
        if (!organisateurId) {
            message.textContent = 'Erreur: Organisateur ID est requis.';
            message.className = 'error';
            return;
        }
        eventData.organisateurId = organisateurId;


        let targetUrl = API_BASE_URL; // API_BASE_URL est 'http://localhost:8080/api/events'
        if (type === 'Conference') {
            targetUrl += '/conference';
        } else if (type === 'Concert') {
            targetUrl += '/concert';
        } else {
            message.textContent = 'Type d\'événement non valide';
            message.className = 'error';
            return;
        }

        fetch(targetUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(eventData) // Envoi en JSON
        })
        .then(response => {
            if (response.ok) {
                return response.json().then(responseData => {
                    message.textContent = responseData.message || 'Événement créé !';
                    message.className = 'success';
                    form.reset();
                    // Cache les champs spécifiques après reset
                    document.getElementById('conference-fields').style.display = 'none';
                    document.getElementById('concert-fields').style.display = 'none';
                });
            } else {
                return response.json().then(err => { throw new Error(err.message || 'Erreur serveur'); });
            }
        })
        .catch(error => {
            message.textContent = `Erreur: ${error.message}`;
            message.className = 'error';
        });
    });
}



function setupAddParticipantForm(eventId) {
    const form = document.getElementById('add-participant-form');
    const message = document.getElementById('participant-message');

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const participantId = form.participantId.value;

        fetch(`${API_BASE_URL}/${eventId}/participants/${participantId}`, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    message.textContent = 'Participant ajouté !';
                    message.className = 'success';
                    form.reset();
                } else {
                    return response.text().then(text => { throw new Error(text); });
                }
            })
            .catch(error => {
                message.textContent = `Erreur: ${error.message}`;
                message.className = 'error';
            });
    });
}

// Dans scripts.js

function setupCreateParticipantForm() {
    const form = document.getElementById('create-participant-form');
    const message = document.getElementById('form-message');

    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const type = formData.get('type'); // 'participant' ou 'organisateur'
        const data = {
            // L'ID est généralement généré par le backend lors de la création.
            // Si vous le générez côté client, assurez-vous que le backend le gère.
            // Pour l'instant, je le retire car vos méthodes actuelles ne le prennent pas.
            // id: crypto.randomUUID(),
            nom: formData.get('nom'),
            email: formData.get('email')
        };

        let targetUrl = '';
        if (type === 'participant') {
            targetUrl = 'http://localhost:8080/api/participants'; // URL directe
        } else { // type === 'organisateur'
            targetUrl = 'http://localhost:8080/api/organisateurs'; // URL directe
        }

        fetch(targetUrl, { // Utilisation de l'URL corrigée
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                // Il est souvent préférable de lire la réponse JSON du backend
                return response.json().then(responseData => {
                    message.textContent = responseData.message || `${type === 'participant' ? 'Participant' : 'Organisateur'} créé !`;
                    message.className = 'success';
                    form.reset();
                });
            } else {
                // Essayer de lire l'erreur du backend si elle est en JSON
                return response.json().then(err => { throw new Error(err.message || 'Erreur serveur'); });
            }
        })
        .catch(error => {
            message.textContent = `Erreur: ${error.message}`;
            message.className = 'error';
        });
    });
}