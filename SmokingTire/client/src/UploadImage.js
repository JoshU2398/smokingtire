import { useParams } from "react-router-dom";

function UploadImage() {

    const {id} = useParams();
    
    function handleClick(e){
        e.preventDefault();

        let data = new FormData();

        data.append("file", document.getElementById("uploadInput").files[0]);

        fetch("http://localhost:8080/api/image/" + id, {
            method: "POST",
            body: data
        })
    }

    return (
        <>
            <form onSubmit={handleClick}>
                <input id="uploadInput" type="file"/>
                <button>Submit</button>
            </form>
        </>
    );
}

export default UploadImage;