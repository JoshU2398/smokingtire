function Make(props) {

    const make = props.makeObj;

    return (
        <option value={make.makeId}>{make.makeName}</option>
    )

}

export default Make;