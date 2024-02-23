import React from 'react'
import { Button, Form } from 'react-bootstrap'

const TextChange = ({ label, text, state, setState, refText, handleSubmit, size }) => {
    let tag
    if (state == 0) {
        tag =
            <div className="d-flex align-items-center" onClick={() => setState(1)}>
                {label}{text}
            </div>
    }
    else if (state == 1) {
        tag =
            <div className="d-flex align-items-center">
                {label}
                <Form.Control className='w-50 mx-2' ref={refText} id='desc' size={size} type='text' placeholder={text} />
                <Button size={size} variant='outline-primary'
                    onClick={() => {
                        handleSubmit()
                        setState(0)
                    }}
                >
                    Submit / Cancel
                </Button>
            </div>
    }
    return (
        <>
            {tag}
        </>
    )
}

export default TextChange