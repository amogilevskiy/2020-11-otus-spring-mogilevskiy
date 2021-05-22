import React from 'react';
import {Space, Table} from 'antd';
import Author from "../../domain/Author";
import {PaginatedList} from "../../services/PaginatedList";

interface AuthorListProps {
    authors: PaginatedList<Author>;
    loading: boolean;
    onEdit: (genre: Author) => void;
    onPageChange: (page: number) => void;
}

const AuthorList: React.FC<AuthorListProps> = ({authors, loading, onEdit, onPageChange}) => {

    const columns = [
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'First name',
            dataIndex: 'firstName',
            key: 'first_name',
        },
        {
            title: 'Last name',
            dataIndex: 'lastName',
            key: 'last_name',
        },
        {
            title: 'Middle name',
            dataIndex: 'middleName',
            key: 'middle_name',
        },
        {
            title: '',
            key: 'options',
            render: (text: any, record: any) => (
                <Space size="middle">
                    <a onClick={() => onEdit(record)}>Edit</a>
                </Space>
            ),
        },
    ];

    return <Table dataSource={authors.content}
                  pagination={{
                      total: authors.totalElements,
                      pageSize: authors.size,
                      current: authors.number + 1,
                      onChange: onPageChange,
                  }}
                  loading={loading}
                  columns={columns}
                  rowKey="id"/>;
};

export default AuthorList;