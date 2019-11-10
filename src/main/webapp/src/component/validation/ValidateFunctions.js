export function validatePrice(value) {
    let error;
    const regularExpression = /(?<=^| )\d+\.\d+(?=$| )/;
    const secRegularExpression = /^[0-9\b]+$/;
    if (!(secRegularExpression).test(value)) {
      error = (value === '' || !regularExpression.test(value))? 'Price must be a number or float and be greater than 0!': '';
    } else {
      error = '';
    }
    return error;
  }

export function validateDuration(value) {
    let error;
    const regularExpression = /^[0-9\b]+$/;
    error = value === '' || !regularExpression.test(value) ? 'Duration must be a number. 0 – indicates this is infinite certificate!': '';
    return error;
  }