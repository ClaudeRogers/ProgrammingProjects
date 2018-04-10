function Badge(props) {
  return React.createElement(
    'div',
    null,
    React.createElement('img', { src: props.img }),
    React.createElement(
      'h1',
      null,
      'Name: ',
      props.name
    ),
    React.createElement(
      'h3',
      null,
      'username: ',
      props.username
    )
  );
}

ReactDOM.render(React.createElement(Badge, {
  name: 'Tyler McGinnis',
  username: 'tylermcginnis',
  img: 'https://avatars0.githubusercontent.com/u/2933430?v=3&s=460'
}), document.getElementById('app'));