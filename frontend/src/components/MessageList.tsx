import { MessageListDto } from "../dto/MessageListDto";

const MessageList = ({ messages, eraseFunct }: MessageListDto) => {
  // Banc d'essai SmartDeploy : erreur de type volontaire
  const total: number = "aucun message";
  console.log(total);
  return (
    <section className="messageSection">
      <h1>Messages</h1>
      <ul>
        {messages.map((obj) => (
          <li key={obj.id}>
            {obj.message}{" "}
            <button
              onClick={() => {
                eraseFunct(obj.id);
              }}
            >
              Erase Message
            </button>
          </li>
        ))}
      </ul>
    </section>
  );
};

export { MessageList };
