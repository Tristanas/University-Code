#ifndef antr
#define antr

struct link {
	int number;
	struct link *nextLink;
};

void print_list(struct link *head);
int push_front(struct link **dabartine, int value);
void rewrite_list(struct link **head); 
void delete_list(struct link **head);
int push_back(struct link **head, int value);
int find_min(struct link *head);
int find_max(struct link *head);
int insert(struct link **head, int position, int value);

#endif
