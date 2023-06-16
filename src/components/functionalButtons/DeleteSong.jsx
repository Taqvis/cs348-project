export default function DeleteSongButton() {
    const deleteSong = () => {
        // your delete song logic here
    }

    return (
        <div className="card">
            <button onClick={deleteSong}>Delete Song</button>
        </div>
    );
}
