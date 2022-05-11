function Model(props) {

    const model = props.modelObj;

    return (
        <option value={model.modelId}>{model.modelName}</option>
    )

}

export default Model;